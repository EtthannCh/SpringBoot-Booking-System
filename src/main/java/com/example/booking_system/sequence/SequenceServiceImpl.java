package com.example.booking_system.sequence;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.sequence.model.SequenceCrudDto;
import com.example.booking_system.sequence.model.SequenceDto;
import com.example.booking_system.sequence.model.SequenceEnum.SequenceResetCondition;

@Service
public class SequenceServiceImpl implements SequenceService {

    private final SequenceRepository sequenceRepository;

    public SequenceServiceImpl(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Override
    public Long createSequence(SequenceCrudDto sqCrud, HeaderCollections header) throws Exception {
        // format must consists of '{', '}', and / (use regex)
        // e.g. {CNP*roomNumber/01012025/0001}
        String regex = "^\\{[a-zA-Z]{3}\\*#{2}/#{8}/#{4}\\}$";
        if (!sqCrud.getFormat().matches(regex)) {
            throw new BusinessException("BOK_SEQUENCE_FORMATNOTVALID");
        }
        return sequenceRepository.create(sqCrud.toRecord(header));
    }

    @Override
    public String generateSequenceNo(String sequenceName, HeaderCollections header) throws BusinessException {
        SequenceDto seq = sequenceRepository.findSequenceBySequenceName(sequenceName)
                .orElseThrow(() -> new BusinessException("BOK_SEQUENCE_SEQUENCENOTFOUND"));
        String format = seq.getFormat().replace("{", "").replace("}", "");
        String[] splittedField = format.split("/");
        String firstField = splittedField[0];
        String dateField = splittedField[1];
        String numberSeqField = splittedField[2];

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedString = formatter.format(new Date()).replace("-", "");

        Integer currentNo = seq.getCurrentNumber() + 1;
        if (seq.getResetCondition().equals(SequenceResetCondition.DAY)) {
            LocalDate lastUpdatedDate = seq.getLastUpdatedAt().toLocalDate();
            LocalDate currentDate = LocalDateTime.now().toLocalDate();
            if (currentDate.getDayOfMonth() > lastUpdatedDate.getDayOfMonth()) {
                sequenceRepository.resetCurrentNumber(seq.getId(), header);
            }
        }
        String numberSeqSubstring = numberSeqField.substring(0, numberSeqField.length() - currentNo.toString().length())
                .replace("#", "0")
                + currentNo;

        StringBuilder sequence = new StringBuilder("");
        sequence.append(firstField + "/");
        sequence.append(formattedString + "/");
        sequence.append(numberSeqSubstring + "");

        sequenceRepository.updateSequenceCurrentNumber(seq.getId(), header);
        return sequence.toString();
    }

}

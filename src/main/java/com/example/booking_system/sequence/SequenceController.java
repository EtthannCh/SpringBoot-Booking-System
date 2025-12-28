package com.example.booking_system.sequence;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.sequence.model.SequenceCrudDto;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("sequence")
public class SequenceController {
    private final SequenceService sequenceService;

    public SequenceController(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createSequence(
            @RequestBody SequenceCrudDto sequenceCrudDto,
            @RequestHeader(value = "user-id") UUID userId,
            @RequestHeader(value = "user-name") String userName) throws Exception {
        HeaderCollections header = new HeaderCollections()
                .setUserId(userId)
                .setUserName(userName);

        return sequenceService.createSequence(sequenceCrudDto, header);
    }

    @PostMapping("/generate-sequence")
    public String generateSequence(
            @RequestParam String seqName,
            @RequestHeader(value = "user-id") UUID userId,
            @RequestHeader(value = "user-name") String userName) throws Exception {
        HeaderCollections header = new HeaderCollections()
                .setUserId(userId)
                .setUserName(userName);
        return sequenceService.generateSequenceNo(seqName.toUpperCase(), header);
    }

}

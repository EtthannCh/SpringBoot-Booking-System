package com.example.booking_system.sequence;

import java.sql.Types;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.sequence.model.Sequence;
import com.example.booking_system.sequence.model.SequenceDto;

@Repository
public class SequenceRepository {
    private final JdbcClient jdbcClient;

    public SequenceRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long create(Sequence sequence) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                insert into sequence
                (
                    name, format, current_number, start_no, reset_condition,
                    created_by_id, created_by, created_at,
                    last_updated_by_id, last_updated_by, last_updated_at
                )
                values
                (
                    :name, :format, :currentNumber, :startNo, :resetCondition,
                    :createdById, :createdBy, now(),
                    :lastUpdatedById, :lastUpdatedBy, now()
                )
                    """)
                .param("name", sequence.name())
                .param("format", sequence.format())
                .param("currentNumber", sequence.current_number())
                .param("startNo", sequence.start_no())
                .param("resetCondition", sequence.reset_condition(), Types.VARCHAR)
                .param("createdById", sequence.created_by_id())
                .param("createdBy", sequence.created_by())
                .param("lastUpdatedById", sequence.last_updated_by_id())
                .param("lastUpdatedBy", sequence.last_updated_by())
                .update(keyHolder, "id");
        var id = keyHolder.getKey();
        return id.longValue();
    }

    public Optional<SequenceDto> findSequenceBySequenceName(String name) {
        return jdbcClient.sql("""
                select *
                from sequence
                where name = :name
                """)
                .param("name", name)
                .query(SequenceDto.class)
                .optional();
    }

    public void updateSequenceCurrentNumber(Long id, HeaderCollections header) throws BusinessException {
        int update = jdbcClient.sql("""
                update sequence
                set 
                    current_number = current_number + 1,
                    last_updated_by = :lastUpdatedBy,
                    last_updated_by_id = :lastUpdatedById,
                    last_updated_at = now()
                where id = :id
                """)
                .param("id", id)
                .param("lastUpdatedBy", header.getUserName())
                .param("lastUpdatedById", header.getUserId())
                .update();
        if(update == 0){
            throw new BusinessException("BOK_SEQUENCE_IDNOTFOUND");
        }
    }

     public Integer resetCurrentNumber(Long id, HeaderCollections header) throws BusinessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcClient.sql("""
                update sequence
                set 
                    current_number = 0,
                    last_updated_by = :lastUpdatedBy,
                    last_updated_by_id = :lastUpdatedById,
                    last_updated_at = now()
                where id = :id
                returning current_number;
                """)
                .param("id", id)
                .param("lastUpdatedBy", header.getUserName())
                .param("lastUpdatedById", header.getUserId())
                .update(keyHolder, "current_number");
        if(update == 0){
            throw new BusinessException("BOK_SEQUENCE_IDNOTFOUND");
        }

        var currentNumber = keyHolder.getKeys().get("current_number");

        return (Integer) currentNumber + 1;
    }
}

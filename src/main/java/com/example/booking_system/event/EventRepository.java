package com.example.booking_system.event;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.event.model.Event;
import com.example.booking_system.event.model.EventDto;

@Repository
public class EventRepository {
    private final JdbcClient jdbcClient;

    public EventRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long createEvent(Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient
                .sql("""
                        insert into events
                        (
                            name, description, location_id, location_name, start_time, 
                            period_start, period_end,
                            created_at, created_by, created_by_id,
                            last_updated_at, last_updated_by, last_updated_by_id
                        )
                        values
                        (
                            :name, :description, :locationId, :locationName, :startTime::varchar[],
                            :periodStart, :periodEnd,
                            now(), :createdBy, :createdById,
                            now(), :lastUpdatedBy, :lastUpdatedById
                        )
                        """)
                .param("name", event.name() != null || !event.name().equals("") ? event.name().toUpperCase() : "")
                .param("description", event.description())
                .param("locationId", event.location_id())
                .param("locationName", event.location_name())
                .param("periodStart", event.period_start())
                .param("periodEnd", event.period_end())
                .param("startTime", event.start_time())
                .param("createdBy", event.created_by())
                .param("createdById", event.created_by_id())
                .param("lastUpdatedBy", event.last_updated_by())
                .param("lastUpdatedById", event.last_updated_by_id())
                .update(keyHolder, "id");
        var id = keyHolder.getKey();
        return id.longValue();
    }

    public Optional<EventDto> findEventById(Long eventId) {
        return jdbcClient.sql("""
                select
                    id,
                    name,
                    location_id,
                    description,
                    period_start,
                    period_end,
                    active,
                    duration,
                    array_to_string(start_time, ',') startTime,
                    created_at,
                    created_by_id,
                    created_by,
                    last_updated_by,
                    last_updated_by_id,
                    last_updated_at
                from events where id = :id
                """)
                .param("id", eventId)
                .query(EventDto.class)
                .optional();
    }

    public List<EventDto> findEventList(Long categoryId) {
        return jdbcClient.sql("""
                select
                     id,
                     name,
                     location_id,
                     description,
                     period_start,
                     period_end,
                     active,
                     duration,
                     array_to_string(start_time, ',') startTime,
                     created_at,
                     created_by_id,
                     created_by,
                     last_updated_by,
                     last_updated_by_id,
                     last_updated_at
                 from events
                 """).query(EventDto.class).list();
    }
}

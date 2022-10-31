package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import network.rain.product.domain.Event;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Event}, with proper type conversions.
 */
@Service
public class EventRowMapper implements BiFunction<Row, String, Event> {

    private final ColumnConverter converter;

    public EventRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Event} stored in the database.
     */
    @Override
    public Event apply(Row row, String prefix) {
        Event entity = new Event();
        entity.setCorrelationId(converter.fromRow(row, prefix + "_correlation_id", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setDomain(converter.fromRow(row, prefix + "_domain", String.class));
        entity.setEventId(converter.fromRow(row, prefix + "_event_id", String.class));
        entity.setEventTime(converter.fromRow(row, prefix + "_event_time", Instant.class));
        entity.setEventType(converter.fromRow(row, prefix + "_event_type", String.class));
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setPriority(converter.fromRow(row, prefix + "_priority", String.class));
        entity.setTimeOccurred(converter.fromRow(row, prefix + "_time_occurred", Instant.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        return entity;
    }
}

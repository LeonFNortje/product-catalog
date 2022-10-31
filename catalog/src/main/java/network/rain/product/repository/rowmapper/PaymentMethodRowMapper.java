package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import network.rain.product.domain.PaymentMethod;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link PaymentMethod}, with proper type conversions.
 */
@Service
public class PaymentMethodRowMapper implements BiFunction<Row, String, PaymentMethod> {

    private final ColumnConverter converter;

    public PaymentMethodRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link PaymentMethod} stored in the database.
     */
    @Override
    public PaymentMethod apply(Row row, String prefix) {
        PaymentMethod entity = new PaymentMethod();
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setAuthorizationCode(converter.fromRow(row, prefix + "_authorization_code", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setIsPreferred(converter.fromRow(row, prefix + "_is_preferred", Boolean.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", String.class));
        entity.setStatusDate(converter.fromRow(row, prefix + "_status_date", Instant.class));
        entity.setStatusReason(converter.fromRow(row, prefix + "_status_reason", String.class));
        entity.setValidForFrom(converter.fromRow(row, prefix + "_valid_for_from", Instant.class));
        entity.setValidForTo(converter.fromRow(row, prefix + "_valid_for_to", Instant.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        entity.setRelatedPlaceId(converter.fromRow(row, prefix + "_related_place_id", String.class));
        entity.setRelatedPartyId(converter.fromRow(row, prefix + "_related_party_id", String.class));
        return entity;
    }
}

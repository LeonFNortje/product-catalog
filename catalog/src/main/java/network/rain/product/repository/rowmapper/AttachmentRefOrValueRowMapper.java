package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import network.rain.product.domain.AttachmentRefOrValue;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link AttachmentRefOrValue}, with proper type conversions.
 */
@Service
public class AttachmentRefOrValueRowMapper implements BiFunction<Row, String, AttachmentRefOrValue> {

    private final ColumnConverter converter;

    public AttachmentRefOrValueRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link AttachmentRefOrValue} stored in the database.
     */
    @Override
    public AttachmentRefOrValue apply(Row row, String prefix) {
        AttachmentRefOrValue entity = new AttachmentRefOrValue();
        entity.setAttachmentType(converter.fromRow(row, prefix + "_attachment_type", String.class));
        entity.setContent(converter.fromRow(row, prefix + "_content", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setMimeType(converter.fromRow(row, prefix + "_mime_type", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setSizeOfBytes(converter.fromRow(row, prefix + "_size_of_bytes", Integer.class));
        entity.setUrl(converter.fromRow(row, prefix + "_url", String.class));
        entity.setValidForFrom(converter.fromRow(row, prefix + "_valid_for_from", Instant.class));
        entity.setValidForTo(converter.fromRow(row, prefix + "_valid_for_to", Instant.class));
        entity.setValueType(converter.fromRow(row, prefix + "_value_type", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

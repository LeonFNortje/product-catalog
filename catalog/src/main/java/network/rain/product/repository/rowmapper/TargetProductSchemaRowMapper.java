package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import network.rain.product.domain.TargetProductSchema;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link TargetProductSchema}, with proper type conversions.
 */
@Service
public class TargetProductSchemaRowMapper implements BiFunction<Row, String, TargetProductSchema> {

    private final ColumnConverter converter;

    public TargetProductSchemaRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link TargetProductSchema} stored in the database.
     */
    @Override
    public TargetProductSchema apply(Row row, String prefix) {
        TargetProductSchema entity = new TargetProductSchema();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

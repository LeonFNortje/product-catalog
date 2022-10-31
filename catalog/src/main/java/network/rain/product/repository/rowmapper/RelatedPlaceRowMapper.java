package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import network.rain.product.domain.RelatedPlace;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RelatedPlace}, with proper type conversions.
 */
@Service
public class RelatedPlaceRowMapper implements BiFunction<Row, String, RelatedPlace> {

    private final ColumnConverter converter;

    public RelatedPlaceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RelatedPlace} stored in the database.
     */
    @Override
    public RelatedPlace apply(Row row, String prefix) {
        RelatedPlace entity = new RelatedPlace();
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setRole(converter.fromRow(row, prefix + "_role", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import network.rain.product.domain.RelatedParty;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RelatedParty}, with proper type conversions.
 */
@Service
public class RelatedPartyRowMapper implements BiFunction<Row, String, RelatedParty> {

    private final ColumnConverter converter;

    public RelatedPartyRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RelatedParty} stored in the database.
     */
    @Override
    public RelatedParty apply(Row row, String prefix) {
        RelatedParty entity = new RelatedParty();
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setRole(converter.fromRow(row, prefix + "_role", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

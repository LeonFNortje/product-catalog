package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import network.rain.product.domain.ProductSpecificationRelationship;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ProductSpecificationRelationship}, with proper type conversions.
 */
@Service
public class ProductSpecificationRelationshipRowMapper implements BiFunction<Row, String, ProductSpecificationRelationship> {

    private final ColumnConverter converter;

    public ProductSpecificationRelationshipRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ProductSpecificationRelationship} stored in the database.
     */
    @Override
    public ProductSpecificationRelationship apply(Row row, String prefix) {
        ProductSpecificationRelationship entity = new ProductSpecificationRelationship();
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setRelationshipType(converter.fromRow(row, prefix + "_relationship_type", String.class));
        entity.setValidForFrom(converter.fromRow(row, prefix + "_valid_for_from", Instant.class));
        entity.setValidForTo(converter.fromRow(row, prefix + "_valid_for_to", Instant.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import network.rain.product.domain.ResourceSpecificationRef;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ResourceSpecificationRef}, with proper type conversions.
 */
@Service
public class ResourceSpecificationRefRowMapper implements BiFunction<Row, String, ResourceSpecificationRef> {

    private final ColumnConverter converter;

    public ResourceSpecificationRefRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ResourceSpecificationRef} stored in the database.
     */
    @Override
    public ResourceSpecificationRef apply(Row row, String prefix) {
        ResourceSpecificationRef entity = new ResourceSpecificationRef();
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setVersion(converter.fromRow(row, prefix + "_version", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

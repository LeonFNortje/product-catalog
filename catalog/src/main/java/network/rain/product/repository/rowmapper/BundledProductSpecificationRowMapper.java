package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import network.rain.product.domain.BundledProductSpecification;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link BundledProductSpecification}, with proper type conversions.
 */
@Service
public class BundledProductSpecificationRowMapper implements BiFunction<Row, String, BundledProductSpecification> {

    private final ColumnConverter converter;

    public BundledProductSpecificationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link BundledProductSpecification} stored in the database.
     */
    @Override
    public BundledProductSpecification apply(Row row, String prefix) {
        BundledProductSpecification entity = new BundledProductSpecification();
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setLifecycleStatus(converter.fromRow(row, prefix + "_lifecycle_status", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

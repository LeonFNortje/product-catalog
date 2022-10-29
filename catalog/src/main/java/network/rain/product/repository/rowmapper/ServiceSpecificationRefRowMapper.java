package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import network.rain.product.domain.ServiceSpecificationRef;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ServiceSpecificationRef}, with proper type conversions.
 */
@Service
public class ServiceSpecificationRefRowMapper implements BiFunction<Row, String, ServiceSpecificationRef> {

    private final ColumnConverter converter;

    public ServiceSpecificationRefRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ServiceSpecificationRef} stored in the database.
     */
    @Override
    public ServiceSpecificationRef apply(Row row, String prefix) {
        ServiceSpecificationRef entity = new ServiceSpecificationRef();
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setVersion(converter.fromRow(row, prefix + "_version", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        return entity;
    }
}

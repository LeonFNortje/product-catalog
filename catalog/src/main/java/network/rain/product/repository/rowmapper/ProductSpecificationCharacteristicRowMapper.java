package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import network.rain.product.domain.ProductSpecificationCharacteristic;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ProductSpecificationCharacteristic}, with proper type conversions.
 */
@Service
public class ProductSpecificationCharacteristicRowMapper implements BiFunction<Row, String, ProductSpecificationCharacteristic> {

    private final ColumnConverter converter;

    public ProductSpecificationCharacteristicRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ProductSpecificationCharacteristic} stored in the database.
     */
    @Override
    public ProductSpecificationCharacteristic apply(Row row, String prefix) {
        ProductSpecificationCharacteristic entity = new ProductSpecificationCharacteristic();
        entity.setConfigurable(converter.fromRow(row, prefix + "_configurable", Boolean.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setExtensible(converter.fromRow(row, prefix + "_extensible", Boolean.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setIsUnique(converter.fromRow(row, prefix + "_is_unique", Boolean.class));
        entity.setMaxCardinality(converter.fromRow(row, prefix + "_max_cardinality", Integer.class));
        entity.setMinCardinality(converter.fromRow(row, prefix + "_min_cardinality", Integer.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setRegex(converter.fromRow(row, prefix + "_regex", String.class));
        entity.setValidForFrom(converter.fromRow(row, prefix + "_valid_for_from", Instant.class));
        entity.setValidForTo(converter.fromRow(row, prefix + "_valid_for_to", Instant.class));
        entity.setValueType(converter.fromRow(row, prefix + "_value_type", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        entity.setValueSchemaLocation(converter.fromRow(row, prefix + "_value_schema_location", String.class));
        entity.setProductSpecificationCharacteristicRelationshipId(
            converter.fromRow(row, prefix + "_product_specification_characteristic_relationship_id", String.class)
        );
        return entity;
    }
}

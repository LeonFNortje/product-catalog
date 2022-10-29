package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import network.rain.product.domain.CharacteristicValueSpecification;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link CharacteristicValueSpecification}, with proper type conversions.
 */
@Service
public class CharacteristicValueSpecificationRowMapper implements BiFunction<Row, String, CharacteristicValueSpecification> {

    private final ColumnConverter converter;

    public CharacteristicValueSpecificationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link CharacteristicValueSpecification} stored in the database.
     */
    @Override
    public CharacteristicValueSpecification apply(Row row, String prefix) {
        CharacteristicValueSpecification entity = new CharacteristicValueSpecification();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setIsDefault(converter.fromRow(row, prefix + "_is_default", Boolean.class));
        entity.setRangeInterval(converter.fromRow(row, prefix + "_range_interval", String.class));
        entity.setRegex(converter.fromRow(row, prefix + "_regex", String.class));
        entity.setUnitOfMeasure(converter.fromRow(row, prefix + "_unit_of_measure", String.class));
        entity.setValidForFrom(converter.fromRow(row, prefix + "_valid_for_from", Instant.class));
        entity.setValidForTo(converter.fromRow(row, prefix + "_valid_for_to", Instant.class));
        entity.setValueType(converter.fromRow(row, prefix + "_value_type", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        entity.setProductSpecificationCharacteristicRelationshipId(
            converter.fromRow(row, prefix + "_product_specification_characteristic_relationship_id", String.class)
        );
        return entity;
    }
}

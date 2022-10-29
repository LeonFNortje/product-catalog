package network.rain.product.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import network.rain.product.domain.ProductSpecification;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ProductSpecification}, with proper type conversions.
 */
@Service
public class ProductSpecificationRowMapper implements BiFunction<Row, String, ProductSpecification> {

    private final ColumnConverter converter;

    public ProductSpecificationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ProductSpecification} stored in the database.
     */
    @Override
    public ProductSpecification apply(Row row, String prefix) {
        ProductSpecification entity = new ProductSpecification();
        entity.setBrand(converter.fromRow(row, prefix + "_brand", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setHref(converter.fromRow(row, prefix + "_href", String.class));
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setIsBundle(converter.fromRow(row, prefix + "_is_bundle", Boolean.class));
        entity.setLastUpdate(converter.fromRow(row, prefix + "_last_update", Instant.class));
        entity.setLifecycleStatus(converter.fromRow(row, prefix + "_lifecycle_status", String.class));
        entity.setProductNumber(converter.fromRow(row, prefix + "_product_number", String.class));
        entity.setValidForFrom(converter.fromRow(row, prefix + "_valid_for_from", Instant.class));
        entity.setValidForTo(converter.fromRow(row, prefix + "_valid_for_to", Instant.class));
        entity.setVersion(converter.fromRow(row, prefix + "_version", String.class));
        entity.setSchemaLocation(converter.fromRow(row, prefix + "_schema_location", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        entity.setTargetProductSchemaId(converter.fromRow(row, prefix + "_target_product_schema_id", Long.class));
        entity.setResourceSpecificationRefId(converter.fromRow(row, prefix + "_resource_specification_ref_id", String.class));
        entity.setAttachmentRefOrValueId(converter.fromRow(row, prefix + "_attachment_ref_or_value_id", String.class));
        entity.setRelatedPartyId(converter.fromRow(row, prefix + "_related_party_id", String.class));
        entity.setServiceSpecificationRefId(converter.fromRow(row, prefix + "_service_specification_ref_id", String.class));
        entity.setProductSpecificationRelationshipId(
            converter.fromRow(row, prefix + "_product_specification_relationship_id", String.class)
        );
        entity.setBundledProductSpecificationId(converter.fromRow(row, prefix + "_bundled_product_specification_id", String.class));
        entity.setProductSpecificationCharacteristicId(
            converter.fromRow(row, prefix + "_product_specification_characteristic_id", String.class)
        );
        return entity;
    }
}

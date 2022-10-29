package network.rain.product.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.ProductSpecificationCharacteristic} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSpecificationCharacteristicDTO implements Serializable {

    private Boolean configurable;

    private String description;

    private Boolean extensible;

    private String id;

    private Boolean isUnique;

    private Integer maxCardinality;

    private Integer minCardinality;

    private String name;

    private String regex;

    private Instant validForFrom;

    private Instant validForTo;

    private String valueType;

    private String schemaLocation;

    private String type;

    private String valueSchemaLocation;

    private ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationship;

    public Boolean getConfigurable() {
        return configurable;
    }

    public void setConfigurable(Boolean configurable) {
        this.configurable = configurable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getExtensible() {
        return extensible;
    }

    public void setExtensible(Boolean extensible) {
        this.extensible = extensible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    public Integer getMaxCardinality() {
        return maxCardinality;
    }

    public void setMaxCardinality(Integer maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    public Integer getMinCardinality() {
        return minCardinality;
    }

    public void setMinCardinality(Integer minCardinality) {
        this.minCardinality = minCardinality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Instant getValidForFrom() {
        return validForFrom;
    }

    public void setValidForFrom(Instant validForFrom) {
        this.validForFrom = validForFrom;
    }

    public Instant getValidForTo() {
        return validForTo;
    }

    public void setValidForTo(Instant validForTo) {
        this.validForTo = validForTo;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueSchemaLocation() {
        return valueSchemaLocation;
    }

    public void setValueSchemaLocation(String valueSchemaLocation) {
        this.valueSchemaLocation = valueSchemaLocation;
    }

    public ProductSpecificationCharacteristicRelationshipDTO getProductSpecificationCharacteristicRelationship() {
        return productSpecificationCharacteristicRelationship;
    }

    public void setProductSpecificationCharacteristicRelationship(
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationship
    ) {
        this.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecificationCharacteristicDTO)) {
            return false;
        }

        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = (ProductSpecificationCharacteristicDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productSpecificationCharacteristicDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecificationCharacteristicDTO{" +
            "configurable='" + getConfigurable() + "'" +
            ", description='" + getDescription() + "'" +
            ", extensible='" + getExtensible() + "'" +
            ", id='" + getId() + "'" +
            ", isUnique='" + getIsUnique() + "'" +
            ", maxCardinality=" + getMaxCardinality() +
            ", minCardinality=" + getMinCardinality() +
            ", name='" + getName() + "'" +
            ", regex='" + getRegex() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            ", valueSchemaLocation='" + getValueSchemaLocation() + "'" +
            ", productSpecificationCharacteristicRelationship=" + getProductSpecificationCharacteristicRelationship() +
            "}";
    }
}

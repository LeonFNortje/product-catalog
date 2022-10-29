package network.rain.product.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.CharacteristicValueSpecification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CharacteristicValueSpecificationDTO implements Serializable {

    private Long id;

    private Boolean isDefault;

    private String rangeInterval;

    private String regex;

    private String unitOfMeasure;

    private Instant validForFrom;

    private Instant validForTo;

    private String valueType;

    private String schemaLocation;

    private String type;

    private ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getRangeInterval() {
        return rangeInterval;
    }

    public void setRangeInterval(String rangeInterval) {
        this.rangeInterval = rangeInterval;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
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
        if (!(o instanceof CharacteristicValueSpecificationDTO)) {
            return false;
        }

        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = (CharacteristicValueSpecificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, characteristicValueSpecificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CharacteristicValueSpecificationDTO{" +
            "id=" + getId() +
            ", isDefault='" + getIsDefault() + "'" +
            ", rangeInterval='" + getRangeInterval() + "'" +
            ", regex='" + getRegex() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            ", productSpecificationCharacteristicRelationship=" + getProductSpecificationCharacteristicRelationship() +
            "}";
    }
}

package network.rain.product.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.ProductSpecificationCharacteristicRelationship} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSpecificationCharacteristicRelationshipDTO implements Serializable {

    private String href;

    private String id;

    private String name;

    private String relationshipType;

    private Instant validForFrom;

    private Instant validForTo;

    private String schemaLocation;

    private String type;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecificationCharacteristicRelationshipDTO)) {
            return false;
        }

        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = (ProductSpecificationCharacteristicRelationshipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productSpecificationCharacteristicRelationshipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecificationCharacteristicRelationshipDTO{" +
            "href='" + getHref() + "'" +
            ", id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", relationshipType='" + getRelationshipType() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

package network.rain.product.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.BundledProductSpecification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BundledProductSpecificationDTO implements Serializable {

    private String href;

    private String id;

    private String name;

    private String lifecycleStatus;

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

    public String getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
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
        if (!(o instanceof BundledProductSpecificationDTO)) {
            return false;
        }

        BundledProductSpecificationDTO bundledProductSpecificationDTO = (BundledProductSpecificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bundledProductSpecificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BundledProductSpecificationDTO{" +
            "href='" + getHref() + "'" +
            ", id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", lifecycleStatus='" + getLifecycleStatus() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

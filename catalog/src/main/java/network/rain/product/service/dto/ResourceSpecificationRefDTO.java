package network.rain.product.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.ResourceSpecificationRef} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResourceSpecificationRefDTO implements Serializable {

    private String href;

    private String id;

    private String name;

    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
        if (!(o instanceof ResourceSpecificationRefDTO)) {
            return false;
        }

        ResourceSpecificationRefDTO resourceSpecificationRefDTO = (ResourceSpecificationRefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resourceSpecificationRefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceSpecificationRefDTO{" +
            "href='" + getHref() + "'" +
            ", id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", version='" + getVersion() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

package network.rain.product.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.RelatedPlace} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelatedPlaceDTO implements Serializable {

    private String id;

    private String href;

    private String name;

    private String role;

    private String schemaLocation;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        if (!(o instanceof RelatedPlaceDTO)) {
            return false;
        }

        RelatedPlaceDTO relatedPlaceDTO = (RelatedPlaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, relatedPlaceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelatedPlaceDTO{" +
            "id='" + getId() + "'" +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", role='" + getRole() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

package network.rain.product.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.TargetProductSchema} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TargetProductSchemaDTO implements Serializable {

    private Long id;

    private String schemaLocation;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof TargetProductSchemaDTO)) {
            return false;
        }

        TargetProductSchemaDTO targetProductSchemaDTO = (TargetProductSchemaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, targetProductSchemaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TargetProductSchemaDTO{" +
            "id=" + getId() +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

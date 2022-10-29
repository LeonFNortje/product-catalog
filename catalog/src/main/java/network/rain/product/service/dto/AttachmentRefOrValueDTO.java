package network.rain.product.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.AttachmentRefOrValue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachmentRefOrValueDTO implements Serializable {

    private String attachmentType;

    private String content;

    private String description;

    private String href;

    private String id;

    private String mimeType;

    private String name;

    private Integer sizeOfBytes;

    private String url;

    private Instant validForFrom;

    private Instant validForTo;

    private String valueType;

    private String schemaLocation;

    private String type;

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSizeOfBytes() {
        return sizeOfBytes;
    }

    public void setSizeOfBytes(Integer sizeOfBytes) {
        this.sizeOfBytes = sizeOfBytes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentRefOrValueDTO)) {
            return false;
        }

        AttachmentRefOrValueDTO attachmentRefOrValueDTO = (AttachmentRefOrValueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attachmentRefOrValueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentRefOrValueDTO{" +
            "attachmentType='" + getAttachmentType() + "'" +
            ", content='" + getContent() + "'" +
            ", description='" + getDescription() + "'" +
            ", href='" + getHref() + "'" +
            ", id='" + getId() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", name='" + getName() + "'" +
            ", sizeOfBytes=" + getSizeOfBytes() +
            ", url='" + getUrl() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

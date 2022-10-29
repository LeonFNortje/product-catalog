package network.rain.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A AttachmentRefOrValue.
 */
@Table("attachment_ref_or_value")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachmentRefOrValue implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column("attachment_type")
    private String attachmentType;

    @Column("content")
    private String content;

    @Column("description")
    private String description;

    @Column("href")
    private String href;

    @Id
    @Column("id")
    private String id;

    @Column("mime_type")
    private String mimeType;

    @Column("name")
    private String name;

    @Column("size_of_bytes")
    private Integer sizeOfBytes;

    @Column("url")
    private String url;

    @Column("valid_for_from")
    private Instant validForFrom;

    @Column("valid_for_to")
    private Instant validForTo;

    @Column("value_type")
    private String valueType;

    @Column("schema_location")
    private String schemaLocation;

    @Column("type")
    private String type;

    @Transient
    private boolean isPersisted;

    @Transient
    @JsonIgnoreProperties(
        value = {
            "targetProductSchema",
            "resourceSpecificationRef",
            "attachmentRefOrValue",
            "relatedParty",
            "serviceSpecificationRef",
            "productSpecificationRelationship",
            "bundledProductSpecification",
            "productSpecificationCharacteristic",
        },
        allowSetters = true
    )
    private Set<ProductSpecification> productSpecifications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getAttachmentType() {
        return this.attachmentType;
    }

    public AttachmentRefOrValue attachmentType(String attachmentType) {
        this.setAttachmentType(attachmentType);
        return this;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getContent() {
        return this.content;
    }

    public AttachmentRefOrValue content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return this.description;
    }

    public AttachmentRefOrValue description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return this.href;
    }

    public AttachmentRefOrValue href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public AttachmentRefOrValue id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public AttachmentRefOrValue mimeType(String mimeType) {
        this.setMimeType(mimeType);
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return this.name;
    }

    public AttachmentRefOrValue name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSizeOfBytes() {
        return this.sizeOfBytes;
    }

    public AttachmentRefOrValue sizeOfBytes(Integer sizeOfBytes) {
        this.setSizeOfBytes(sizeOfBytes);
        return this;
    }

    public void setSizeOfBytes(Integer sizeOfBytes) {
        this.sizeOfBytes = sizeOfBytes;
    }

    public String getUrl() {
        return this.url;
    }

    public AttachmentRefOrValue url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getValidForFrom() {
        return this.validForFrom;
    }

    public AttachmentRefOrValue validForFrom(Instant validForFrom) {
        this.setValidForFrom(validForFrom);
        return this;
    }

    public void setValidForFrom(Instant validForFrom) {
        this.validForFrom = validForFrom;
    }

    public Instant getValidForTo() {
        return this.validForTo;
    }

    public AttachmentRefOrValue validForTo(Instant validForTo) {
        this.setValidForTo(validForTo);
        return this;
    }

    public void setValidForTo(Instant validForTo) {
        this.validForTo = validForTo;
    }

    public String getValueType() {
        return this.valueType;
    }

    public AttachmentRefOrValue valueType(String valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public AttachmentRefOrValue schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public AttachmentRefOrValue type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public AttachmentRefOrValue setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<ProductSpecification> getProductSpecifications() {
        return this.productSpecifications;
    }

    public void setProductSpecifications(Set<ProductSpecification> productSpecifications) {
        if (this.productSpecifications != null) {
            this.productSpecifications.forEach(i -> i.setAttachmentRefOrValue(null));
        }
        if (productSpecifications != null) {
            productSpecifications.forEach(i -> i.setAttachmentRefOrValue(this));
        }
        this.productSpecifications = productSpecifications;
    }

    public AttachmentRefOrValue productSpecifications(Set<ProductSpecification> productSpecifications) {
        this.setProductSpecifications(productSpecifications);
        return this;
    }

    public AttachmentRefOrValue addProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.add(productSpecification);
        productSpecification.setAttachmentRefOrValue(this);
        return this;
    }

    public AttachmentRefOrValue removeProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.remove(productSpecification);
        productSpecification.setAttachmentRefOrValue(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentRefOrValue)) {
            return false;
        }
        return id != null && id.equals(((AttachmentRefOrValue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentRefOrValue{" +
            "id=" + getId() +
            ", attachmentType='" + getAttachmentType() + "'" +
            ", content='" + getContent() + "'" +
            ", description='" + getDescription() + "'" +
            ", href='" + getHref() + "'" +
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

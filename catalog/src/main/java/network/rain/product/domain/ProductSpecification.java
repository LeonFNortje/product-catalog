package network.rain.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A ProductSpecification.
 */
@Table("product_specification")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSpecification implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column("brand")
    private String brand;

    @Column("description")
    private String description;

    @Column("href")
    private String href;

    @Id
    @Column("id")
    private String id;

    @Column("name")
    private String name;

    @Column("is_bundle")
    private Boolean isBundle;

    @Column("last_update")
    private Instant lastUpdate;

    @Column("lifecycle_status")
    private String lifecycleStatus;

    @Column("product_number")
    private String productNumber;

    @Column("valid_for_from")
    private Instant validForFrom;

    @Column("valid_for_to")
    private Instant validForTo;

    @Column("version")
    private String version;

    @Column("schema_location")
    private String schemaLocation;

    @Column("type")
    private String type;

    @Transient
    private boolean isPersisted;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private TargetProductSchema targetProductSchema;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private ResourceSpecificationRef resourceSpecificationRef;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private AttachmentRefOrValue attachmentRefOrValue;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private RelatedParty relatedParty;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private ServiceSpecificationRef serviceSpecificationRef;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private ProductSpecificationRelationship productSpecificationRelationship;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private BundledProductSpecification bundledProductSpecification;

    @Transient
    @JsonIgnoreProperties(value = { "productSpecifications", "productSpecificationCharacteristicRelationship" }, allowSetters = true)
    private ProductSpecificationCharacteristic productSpecificationCharacteristic;

    @Column("target_product_schema_id")
    private Long targetProductSchemaId;

    @Column("resource_specification_ref_id")
    private String resourceSpecificationRefId;

    @Column("attachment_ref_or_value_id")
    private String attachmentRefOrValueId;

    @Column("related_party_id")
    private String relatedPartyId;

    @Column("service_specification_ref_id")
    private String serviceSpecificationRefId;

    @Column("product_specification_relationship_id")
    private String productSpecificationRelationshipId;

    @Column("bundled_product_specification_id")
    private String bundledProductSpecificationId;

    @Column("product_specification_characteristic_id")
    private String productSpecificationCharacteristicId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getBrand() {
        return this.brand;
    }

    public ProductSpecification brand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductSpecification description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return this.href;
    }

    public ProductSpecification href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public ProductSpecification id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProductSpecification name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsBundle() {
        return this.isBundle;
    }

    public ProductSpecification isBundle(Boolean isBundle) {
        this.setIsBundle(isBundle);
        return this;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public Instant getLastUpdate() {
        return this.lastUpdate;
    }

    public ProductSpecification lastUpdate(Instant lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLifecycleStatus() {
        return this.lifecycleStatus;
    }

    public ProductSpecification lifecycleStatus(String lifecycleStatus) {
        this.setLifecycleStatus(lifecycleStatus);
        return this;
    }

    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public String getProductNumber() {
        return this.productNumber;
    }

    public ProductSpecification productNumber(String productNumber) {
        this.setProductNumber(productNumber);
        return this;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Instant getValidForFrom() {
        return this.validForFrom;
    }

    public ProductSpecification validForFrom(Instant validForFrom) {
        this.setValidForFrom(validForFrom);
        return this;
    }

    public void setValidForFrom(Instant validForFrom) {
        this.validForFrom = validForFrom;
    }

    public Instant getValidForTo() {
        return this.validForTo;
    }

    public ProductSpecification validForTo(Instant validForTo) {
        this.setValidForTo(validForTo);
        return this;
    }

    public void setValidForTo(Instant validForTo) {
        this.validForTo = validForTo;
    }

    public String getVersion() {
        return this.version;
    }

    public ProductSpecification version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public ProductSpecification schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public ProductSpecification type(String type) {
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

    public ProductSpecification setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public TargetProductSchema getTargetProductSchema() {
        return this.targetProductSchema;
    }

    public void setTargetProductSchema(TargetProductSchema targetProductSchema) {
        this.targetProductSchema = targetProductSchema;
        this.targetProductSchemaId = targetProductSchema != null ? targetProductSchema.getId() : null;
    }

    public ProductSpecification targetProductSchema(TargetProductSchema targetProductSchema) {
        this.setTargetProductSchema(targetProductSchema);
        return this;
    }

    public ResourceSpecificationRef getResourceSpecificationRef() {
        return this.resourceSpecificationRef;
    }

    public void setResourceSpecificationRef(ResourceSpecificationRef resourceSpecificationRef) {
        this.resourceSpecificationRef = resourceSpecificationRef;
        this.resourceSpecificationRefId = resourceSpecificationRef != null ? resourceSpecificationRef.getId() : null;
    }

    public ProductSpecification resourceSpecificationRef(ResourceSpecificationRef resourceSpecificationRef) {
        this.setResourceSpecificationRef(resourceSpecificationRef);
        return this;
    }

    public AttachmentRefOrValue getAttachmentRefOrValue() {
        return this.attachmentRefOrValue;
    }

    public void setAttachmentRefOrValue(AttachmentRefOrValue attachmentRefOrValue) {
        this.attachmentRefOrValue = attachmentRefOrValue;
        this.attachmentRefOrValueId = attachmentRefOrValue != null ? attachmentRefOrValue.getId() : null;
    }

    public ProductSpecification attachmentRefOrValue(AttachmentRefOrValue attachmentRefOrValue) {
        this.setAttachmentRefOrValue(attachmentRefOrValue);
        return this;
    }

    public RelatedParty getRelatedParty() {
        return this.relatedParty;
    }

    public void setRelatedParty(RelatedParty relatedParty) {
        this.relatedParty = relatedParty;
        this.relatedPartyId = relatedParty != null ? relatedParty.getId() : null;
    }

    public ProductSpecification relatedParty(RelatedParty relatedParty) {
        this.setRelatedParty(relatedParty);
        return this;
    }

    public ServiceSpecificationRef getServiceSpecificationRef() {
        return this.serviceSpecificationRef;
    }

    public void setServiceSpecificationRef(ServiceSpecificationRef serviceSpecificationRef) {
        this.serviceSpecificationRef = serviceSpecificationRef;
        this.serviceSpecificationRefId = serviceSpecificationRef != null ? serviceSpecificationRef.getId() : null;
    }

    public ProductSpecification serviceSpecificationRef(ServiceSpecificationRef serviceSpecificationRef) {
        this.setServiceSpecificationRef(serviceSpecificationRef);
        return this;
    }

    public ProductSpecificationRelationship getProductSpecificationRelationship() {
        return this.productSpecificationRelationship;
    }

    public void setProductSpecificationRelationship(ProductSpecificationRelationship productSpecificationRelationship) {
        this.productSpecificationRelationship = productSpecificationRelationship;
        this.productSpecificationRelationshipId =
            productSpecificationRelationship != null ? productSpecificationRelationship.getId() : null;
    }

    public ProductSpecification productSpecificationRelationship(ProductSpecificationRelationship productSpecificationRelationship) {
        this.setProductSpecificationRelationship(productSpecificationRelationship);
        return this;
    }

    public BundledProductSpecification getBundledProductSpecification() {
        return this.bundledProductSpecification;
    }

    public void setBundledProductSpecification(BundledProductSpecification bundledProductSpecification) {
        this.bundledProductSpecification = bundledProductSpecification;
        this.bundledProductSpecificationId = bundledProductSpecification != null ? bundledProductSpecification.getId() : null;
    }

    public ProductSpecification bundledProductSpecification(BundledProductSpecification bundledProductSpecification) {
        this.setBundledProductSpecification(bundledProductSpecification);
        return this;
    }

    public ProductSpecificationCharacteristic getProductSpecificationCharacteristic() {
        return this.productSpecificationCharacteristic;
    }

    public void setProductSpecificationCharacteristic(ProductSpecificationCharacteristic productSpecificationCharacteristic) {
        this.productSpecificationCharacteristic = productSpecificationCharacteristic;
        this.productSpecificationCharacteristicId =
            productSpecificationCharacteristic != null ? productSpecificationCharacteristic.getId() : null;
    }

    public ProductSpecification productSpecificationCharacteristic(ProductSpecificationCharacteristic productSpecificationCharacteristic) {
        this.setProductSpecificationCharacteristic(productSpecificationCharacteristic);
        return this;
    }

    public Long getTargetProductSchemaId() {
        return this.targetProductSchemaId;
    }

    public void setTargetProductSchemaId(Long targetProductSchema) {
        this.targetProductSchemaId = targetProductSchema;
    }

    public String getResourceSpecificationRefId() {
        return this.resourceSpecificationRefId;
    }

    public void setResourceSpecificationRefId(String resourceSpecificationRef) {
        this.resourceSpecificationRefId = resourceSpecificationRef;
    }

    public String getAttachmentRefOrValueId() {
        return this.attachmentRefOrValueId;
    }

    public void setAttachmentRefOrValueId(String attachmentRefOrValue) {
        this.attachmentRefOrValueId = attachmentRefOrValue;
    }

    public String getRelatedPartyId() {
        return this.relatedPartyId;
    }

    public void setRelatedPartyId(String relatedParty) {
        this.relatedPartyId = relatedParty;
    }

    public String getServiceSpecificationRefId() {
        return this.serviceSpecificationRefId;
    }

    public void setServiceSpecificationRefId(String serviceSpecificationRef) {
        this.serviceSpecificationRefId = serviceSpecificationRef;
    }

    public String getProductSpecificationRelationshipId() {
        return this.productSpecificationRelationshipId;
    }

    public void setProductSpecificationRelationshipId(String productSpecificationRelationship) {
        this.productSpecificationRelationshipId = productSpecificationRelationship;
    }

    public String getBundledProductSpecificationId() {
        return this.bundledProductSpecificationId;
    }

    public void setBundledProductSpecificationId(String bundledProductSpecification) {
        this.bundledProductSpecificationId = bundledProductSpecification;
    }

    public String getProductSpecificationCharacteristicId() {
        return this.productSpecificationCharacteristicId;
    }

    public void setProductSpecificationCharacteristicId(String productSpecificationCharacteristic) {
        this.productSpecificationCharacteristicId = productSpecificationCharacteristic;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecification)) {
            return false;
        }
        return id != null && id.equals(((ProductSpecification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecification{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", description='" + getDescription() + "'" +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", isBundle='" + getIsBundle() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", lifecycleStatus='" + getLifecycleStatus() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", version='" + getVersion() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

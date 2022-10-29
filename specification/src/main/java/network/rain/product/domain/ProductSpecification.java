package network.rain.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A ProductSpecification.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "product_specification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSpecification implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "href")
    private String href;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_bundle")
    private Boolean isBundle;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(name = "lifecycle_status")
    private String lifecycleStatus;

    @Column(name = "product_number")
    private String productNumber;

    @Column(name = "valid_for_from")
    private Instant validForFrom;

    @Column(name = "valid_for_to")
    private Instant validForTo;

    @Column(name = "version")
    private String version;

    @Column(name = "schema_location")
    private String schemaLocation;

    @Column(name = "type")
    private String type;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private TargetProductSchema targetProductSchema;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private ResourceSpecificationRef resourceSpecificationRef;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private AttachmentRefOrValue attachmentRefOrValue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private RelatedParty relatedParty;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private ServiceSpecificationRef serviceSpecificationRef;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private ProductSpecificationRelationship productSpecificationRelationship;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications" }, allowSetters = true)
    private BundledProductSpecification bundledProductSpecification;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecifications", "productSpecificationCharacteristicRelationship" }, allowSetters = true)
    private ProductSpecificationCharacteristic productSpecificationCharacteristic;

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

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public TargetProductSchema getTargetProductSchema() {
        return this.targetProductSchema;
    }

    public void setTargetProductSchema(TargetProductSchema targetProductSchema) {
        this.targetProductSchema = targetProductSchema;
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
    }

    public ProductSpecification productSpecificationCharacteristic(ProductSpecificationCharacteristic productSpecificationCharacteristic) {
        this.setProductSpecificationCharacteristic(productSpecificationCharacteristic);
        return this;
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

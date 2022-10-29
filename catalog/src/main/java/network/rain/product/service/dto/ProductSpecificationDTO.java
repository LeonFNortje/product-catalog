package network.rain.product.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link network.rain.product.domain.ProductSpecification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSpecificationDTO implements Serializable {

    private String brand;

    private String description;

    private String href;

    private String id;

    private String name;

    private Boolean isBundle;

    private Instant lastUpdate;

    private String lifecycleStatus;

    private String productNumber;

    private Instant validForFrom;

    private Instant validForTo;

    private String version;

    private String schemaLocation;

    private String type;

    private TargetProductSchemaDTO targetProductSchema;

    private ResourceSpecificationRefDTO resourceSpecificationRef;

    private AttachmentRefOrValueDTO attachmentRefOrValue;

    private RelatedPartyDTO relatedParty;

    private ServiceSpecificationRefDTO serviceSpecificationRef;

    private ProductSpecificationRelationshipDTO productSpecificationRelationship;

    private BundledProductSpecificationDTO bundledProductSpecification;

    private ProductSpecificationCharacteristicDTO productSpecificationCharacteristic;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsBundle() {
        return isBundle;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
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

    public TargetProductSchemaDTO getTargetProductSchema() {
        return targetProductSchema;
    }

    public void setTargetProductSchema(TargetProductSchemaDTO targetProductSchema) {
        this.targetProductSchema = targetProductSchema;
    }

    public ResourceSpecificationRefDTO getResourceSpecificationRef() {
        return resourceSpecificationRef;
    }

    public void setResourceSpecificationRef(ResourceSpecificationRefDTO resourceSpecificationRef) {
        this.resourceSpecificationRef = resourceSpecificationRef;
    }

    public AttachmentRefOrValueDTO getAttachmentRefOrValue() {
        return attachmentRefOrValue;
    }

    public void setAttachmentRefOrValue(AttachmentRefOrValueDTO attachmentRefOrValue) {
        this.attachmentRefOrValue = attachmentRefOrValue;
    }

    public RelatedPartyDTO getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(RelatedPartyDTO relatedParty) {
        this.relatedParty = relatedParty;
    }

    public ServiceSpecificationRefDTO getServiceSpecificationRef() {
        return serviceSpecificationRef;
    }

    public void setServiceSpecificationRef(ServiceSpecificationRefDTO serviceSpecificationRef) {
        this.serviceSpecificationRef = serviceSpecificationRef;
    }

    public ProductSpecificationRelationshipDTO getProductSpecificationRelationship() {
        return productSpecificationRelationship;
    }

    public void setProductSpecificationRelationship(ProductSpecificationRelationshipDTO productSpecificationRelationship) {
        this.productSpecificationRelationship = productSpecificationRelationship;
    }

    public BundledProductSpecificationDTO getBundledProductSpecification() {
        return bundledProductSpecification;
    }

    public void setBundledProductSpecification(BundledProductSpecificationDTO bundledProductSpecification) {
        this.bundledProductSpecification = bundledProductSpecification;
    }

    public ProductSpecificationCharacteristicDTO getProductSpecificationCharacteristic() {
        return productSpecificationCharacteristic;
    }

    public void setProductSpecificationCharacteristic(ProductSpecificationCharacteristicDTO productSpecificationCharacteristic) {
        this.productSpecificationCharacteristic = productSpecificationCharacteristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecificationDTO)) {
            return false;
        }

        ProductSpecificationDTO productSpecificationDTO = (ProductSpecificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productSpecificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecificationDTO{" +
            "brand='" + getBrand() + "'" +
            ", description='" + getDescription() + "'" +
            ", href='" + getHref() + "'" +
            ", id='" + getId() + "'" +
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
            ", targetProductSchema=" + getTargetProductSchema() +
            ", resourceSpecificationRef=" + getResourceSpecificationRef() +
            ", attachmentRefOrValue=" + getAttachmentRefOrValue() +
            ", relatedParty=" + getRelatedParty() +
            ", serviceSpecificationRef=" + getServiceSpecificationRef() +
            ", productSpecificationRelationship=" + getProductSpecificationRelationship() +
            ", bundledProductSpecification=" + getBundledProductSpecification() +
            ", productSpecificationCharacteristic=" + getProductSpecificationCharacteristic() +
            "}";
    }
}

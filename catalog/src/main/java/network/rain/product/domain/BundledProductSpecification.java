package network.rain.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A BundledProductSpecification.
 */
@Table("bundled_product_specification")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BundledProductSpecification implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column("href")
    private String href;

    @Id
    @Column("id")
    private String id;

    @Column("name")
    private String name;

    @Column("lifecycle_status")
    private String lifecycleStatus;

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

    public String getHref() {
        return this.href;
    }

    public BundledProductSpecification href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public BundledProductSpecification id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BundledProductSpecification name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLifecycleStatus() {
        return this.lifecycleStatus;
    }

    public BundledProductSpecification lifecycleStatus(String lifecycleStatus) {
        this.setLifecycleStatus(lifecycleStatus);
        return this;
    }

    public void setLifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public BundledProductSpecification schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public BundledProductSpecification type(String type) {
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

    public BundledProductSpecification setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<ProductSpecification> getProductSpecifications() {
        return this.productSpecifications;
    }

    public void setProductSpecifications(Set<ProductSpecification> productSpecifications) {
        if (this.productSpecifications != null) {
            this.productSpecifications.forEach(i -> i.setBundledProductSpecification(null));
        }
        if (productSpecifications != null) {
            productSpecifications.forEach(i -> i.setBundledProductSpecification(this));
        }
        this.productSpecifications = productSpecifications;
    }

    public BundledProductSpecification productSpecifications(Set<ProductSpecification> productSpecifications) {
        this.setProductSpecifications(productSpecifications);
        return this;
    }

    public BundledProductSpecification addProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.add(productSpecification);
        productSpecification.setBundledProductSpecification(this);
        return this;
    }

    public BundledProductSpecification removeProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.remove(productSpecification);
        productSpecification.setBundledProductSpecification(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BundledProductSpecification)) {
            return false;
        }
        return id != null && id.equals(((BundledProductSpecification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BundledProductSpecification{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", lifecycleStatus='" + getLifecycleStatus() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

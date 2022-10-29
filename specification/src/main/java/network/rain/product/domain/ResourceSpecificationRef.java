package network.rain.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A ResourceSpecificationRef.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "resource_specification_ref")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResourceSpecificationRef implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "href")
    private String href;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "schema_location")
    private String schemaLocation;

    @Column(name = "type")
    private String type;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "resourceSpecificationRef")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    public ResourceSpecificationRef href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public ResourceSpecificationRef id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ResourceSpecificationRef name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public ResourceSpecificationRef version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public ResourceSpecificationRef schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public ResourceSpecificationRef type(String type) {
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

    public ResourceSpecificationRef setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<ProductSpecification> getProductSpecifications() {
        return this.productSpecifications;
    }

    public void setProductSpecifications(Set<ProductSpecification> productSpecifications) {
        if (this.productSpecifications != null) {
            this.productSpecifications.forEach(i -> i.setResourceSpecificationRef(null));
        }
        if (productSpecifications != null) {
            productSpecifications.forEach(i -> i.setResourceSpecificationRef(this));
        }
        this.productSpecifications = productSpecifications;
    }

    public ResourceSpecificationRef productSpecifications(Set<ProductSpecification> productSpecifications) {
        this.setProductSpecifications(productSpecifications);
        return this;
    }

    public ResourceSpecificationRef addProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.add(productSpecification);
        productSpecification.setResourceSpecificationRef(this);
        return this;
    }

    public ResourceSpecificationRef removeProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.remove(productSpecification);
        productSpecification.setResourceSpecificationRef(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceSpecificationRef)) {
            return false;
        }
        return id != null && id.equals(((ResourceSpecificationRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceSpecificationRef{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", version='" + getVersion() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

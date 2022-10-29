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
 * A ServiceSpecificationRef.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "service_specification_ref")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceSpecificationRef implements Serializable, Persistable<String> {

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

    @OneToMany(mappedBy = "serviceSpecificationRef")
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

    public ServiceSpecificationRef href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public ServiceSpecificationRef id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ServiceSpecificationRef name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public ServiceSpecificationRef version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public ServiceSpecificationRef schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public ServiceSpecificationRef type(String type) {
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

    public ServiceSpecificationRef setIsPersisted() {
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
            this.productSpecifications.forEach(i -> i.setServiceSpecificationRef(null));
        }
        if (productSpecifications != null) {
            productSpecifications.forEach(i -> i.setServiceSpecificationRef(this));
        }
        this.productSpecifications = productSpecifications;
    }

    public ServiceSpecificationRef productSpecifications(Set<ProductSpecification> productSpecifications) {
        this.setProductSpecifications(productSpecifications);
        return this;
    }

    public ServiceSpecificationRef addProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.add(productSpecification);
        productSpecification.setServiceSpecificationRef(this);
        return this;
    }

    public ServiceSpecificationRef removeProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.remove(productSpecification);
        productSpecification.setServiceSpecificationRef(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceSpecificationRef)) {
            return false;
        }
        return id != null && id.equals(((ServiceSpecificationRef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceSpecificationRef{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", version='" + getVersion() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

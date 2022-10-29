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
 * A RelatedParty.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "related_party")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelatedParty implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "href")
    private String href;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "schema_location")
    private String schemaLocation;

    @Column(name = "type")
    private String type;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "relatedParty")
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

    public RelatedParty href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public RelatedParty id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RelatedParty name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return this.role;
    }

    public RelatedParty role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public RelatedParty schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public RelatedParty type(String type) {
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

    public RelatedParty setIsPersisted() {
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
            this.productSpecifications.forEach(i -> i.setRelatedParty(null));
        }
        if (productSpecifications != null) {
            productSpecifications.forEach(i -> i.setRelatedParty(this));
        }
        this.productSpecifications = productSpecifications;
    }

    public RelatedParty productSpecifications(Set<ProductSpecification> productSpecifications) {
        this.setProductSpecifications(productSpecifications);
        return this;
    }

    public RelatedParty addProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.add(productSpecification);
        productSpecification.setRelatedParty(this);
        return this;
    }

    public RelatedParty removeProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.remove(productSpecification);
        productSpecification.setRelatedParty(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelatedParty)) {
            return false;
        }
        return id != null && id.equals(((RelatedParty) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelatedParty{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", role='" + getRole() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

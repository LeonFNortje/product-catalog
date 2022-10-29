package network.rain.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A ProductSpecificationCharacteristicRelationship.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "product_specification_characteristic_relationship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSpecificationCharacteristicRelationship implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "href")
    private String href;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "relationship_type")
    private String relationshipType;

    @Column(name = "valid_for_from")
    private Instant validForFrom;

    @Column(name = "valid_for_to")
    private Instant validForTo;

    @Column(name = "schema_location")
    private String schemaLocation;

    @Column(name = "type")
    private String type;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "productSpecificationCharacteristicRelationship")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productSpecifications", "productSpecificationCharacteristicRelationship" }, allowSetters = true)
    private Set<ProductSpecificationCharacteristic> productSpecificationCharacteristics = new HashSet<>();

    @OneToMany(mappedBy = "productSpecificationCharacteristicRelationship")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productSpecificationCharacteristicRelationship" }, allowSetters = true)
    private Set<CharacteristicValueSpecification> characteristicValueSpecifications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getHref() {
        return this.href;
    }

    public ProductSpecificationCharacteristicRelationship href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public ProductSpecificationCharacteristicRelationship id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProductSpecificationCharacteristicRelationship name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationshipType() {
        return this.relationshipType;
    }

    public ProductSpecificationCharacteristicRelationship relationshipType(String relationshipType) {
        this.setRelationshipType(relationshipType);
        return this;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Instant getValidForFrom() {
        return this.validForFrom;
    }

    public ProductSpecificationCharacteristicRelationship validForFrom(Instant validForFrom) {
        this.setValidForFrom(validForFrom);
        return this;
    }

    public void setValidForFrom(Instant validForFrom) {
        this.validForFrom = validForFrom;
    }

    public Instant getValidForTo() {
        return this.validForTo;
    }

    public ProductSpecificationCharacteristicRelationship validForTo(Instant validForTo) {
        this.setValidForTo(validForTo);
        return this;
    }

    public void setValidForTo(Instant validForTo) {
        this.validForTo = validForTo;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public ProductSpecificationCharacteristicRelationship schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public ProductSpecificationCharacteristicRelationship type(String type) {
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

    public ProductSpecificationCharacteristicRelationship setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<ProductSpecificationCharacteristic> getProductSpecificationCharacteristics() {
        return this.productSpecificationCharacteristics;
    }

    public void setProductSpecificationCharacteristics(Set<ProductSpecificationCharacteristic> productSpecificationCharacteristics) {
        if (this.productSpecificationCharacteristics != null) {
            this.productSpecificationCharacteristics.forEach(i -> i.setProductSpecificationCharacteristicRelationship(null));
        }
        if (productSpecificationCharacteristics != null) {
            productSpecificationCharacteristics.forEach(i -> i.setProductSpecificationCharacteristicRelationship(this));
        }
        this.productSpecificationCharacteristics = productSpecificationCharacteristics;
    }

    public ProductSpecificationCharacteristicRelationship productSpecificationCharacteristics(
        Set<ProductSpecificationCharacteristic> productSpecificationCharacteristics
    ) {
        this.setProductSpecificationCharacteristics(productSpecificationCharacteristics);
        return this;
    }

    public ProductSpecificationCharacteristicRelationship addProductSpecificationCharacteristic(
        ProductSpecificationCharacteristic productSpecificationCharacteristic
    ) {
        this.productSpecificationCharacteristics.add(productSpecificationCharacteristic);
        productSpecificationCharacteristic.setProductSpecificationCharacteristicRelationship(this);
        return this;
    }

    public ProductSpecificationCharacteristicRelationship removeProductSpecificationCharacteristic(
        ProductSpecificationCharacteristic productSpecificationCharacteristic
    ) {
        this.productSpecificationCharacteristics.remove(productSpecificationCharacteristic);
        productSpecificationCharacteristic.setProductSpecificationCharacteristicRelationship(null);
        return this;
    }

    public Set<CharacteristicValueSpecification> getCharacteristicValueSpecifications() {
        return this.characteristicValueSpecifications;
    }

    public void setCharacteristicValueSpecifications(Set<CharacteristicValueSpecification> characteristicValueSpecifications) {
        if (this.characteristicValueSpecifications != null) {
            this.characteristicValueSpecifications.forEach(i -> i.setProductSpecificationCharacteristicRelationship(null));
        }
        if (characteristicValueSpecifications != null) {
            characteristicValueSpecifications.forEach(i -> i.setProductSpecificationCharacteristicRelationship(this));
        }
        this.characteristicValueSpecifications = characteristicValueSpecifications;
    }

    public ProductSpecificationCharacteristicRelationship characteristicValueSpecifications(
        Set<CharacteristicValueSpecification> characteristicValueSpecifications
    ) {
        this.setCharacteristicValueSpecifications(characteristicValueSpecifications);
        return this;
    }

    public ProductSpecificationCharacteristicRelationship addCharacteristicValueSpecification(
        CharacteristicValueSpecification characteristicValueSpecification
    ) {
        this.characteristicValueSpecifications.add(characteristicValueSpecification);
        characteristicValueSpecification.setProductSpecificationCharacteristicRelationship(this);
        return this;
    }

    public ProductSpecificationCharacteristicRelationship removeCharacteristicValueSpecification(
        CharacteristicValueSpecification characteristicValueSpecification
    ) {
        this.characteristicValueSpecifications.remove(characteristicValueSpecification);
        characteristicValueSpecification.setProductSpecificationCharacteristicRelationship(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecificationCharacteristicRelationship)) {
            return false;
        }
        return id != null && id.equals(((ProductSpecificationCharacteristicRelationship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecificationCharacteristicRelationship{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", name='" + getName() + "'" +
            ", relationshipType='" + getRelationshipType() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

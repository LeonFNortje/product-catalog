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
 * A ProductSpecificationCharacteristic.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "product_specification_characteristic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSpecificationCharacteristic implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "configurable")
    private Boolean configurable;

    @Column(name = "description")
    private String description;

    @Column(name = "extensible")
    private Boolean extensible;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "is_unique")
    private Boolean isUnique;

    @Column(name = "max_cardinality")
    private Integer maxCardinality;

    @Column(name = "min_cardinality")
    private Integer minCardinality;

    @Column(name = "name")
    private String name;

    @Column(name = "regex")
    private String regex;

    @Column(name = "valid_for_from")
    private Instant validForFrom;

    @Column(name = "valid_for_to")
    private Instant validForTo;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "schema_location")
    private String schemaLocation;

    @Column(name = "type")
    private String type;

    @Column(name = "value_schema_location")
    private String valueSchemaLocation;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "productSpecificationCharacteristic")
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecificationCharacteristics", "characteristicValueSpecifications" }, allowSetters = true)
    private ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Boolean getConfigurable() {
        return this.configurable;
    }

    public ProductSpecificationCharacteristic configurable(Boolean configurable) {
        this.setConfigurable(configurable);
        return this;
    }

    public void setConfigurable(Boolean configurable) {
        this.configurable = configurable;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductSpecificationCharacteristic description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getExtensible() {
        return this.extensible;
    }

    public ProductSpecificationCharacteristic extensible(Boolean extensible) {
        this.setExtensible(extensible);
        return this;
    }

    public void setExtensible(Boolean extensible) {
        this.extensible = extensible;
    }

    public String getId() {
        return this.id;
    }

    public ProductSpecificationCharacteristic id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsUnique() {
        return this.isUnique;
    }

    public ProductSpecificationCharacteristic isUnique(Boolean isUnique) {
        this.setIsUnique(isUnique);
        return this;
    }

    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    public Integer getMaxCardinality() {
        return this.maxCardinality;
    }

    public ProductSpecificationCharacteristic maxCardinality(Integer maxCardinality) {
        this.setMaxCardinality(maxCardinality);
        return this;
    }

    public void setMaxCardinality(Integer maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    public Integer getMinCardinality() {
        return this.minCardinality;
    }

    public ProductSpecificationCharacteristic minCardinality(Integer minCardinality) {
        this.setMinCardinality(minCardinality);
        return this;
    }

    public void setMinCardinality(Integer minCardinality) {
        this.minCardinality = minCardinality;
    }

    public String getName() {
        return this.name;
    }

    public ProductSpecificationCharacteristic name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return this.regex;
    }

    public ProductSpecificationCharacteristic regex(String regex) {
        this.setRegex(regex);
        return this;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Instant getValidForFrom() {
        return this.validForFrom;
    }

    public ProductSpecificationCharacteristic validForFrom(Instant validForFrom) {
        this.setValidForFrom(validForFrom);
        return this;
    }

    public void setValidForFrom(Instant validForFrom) {
        this.validForFrom = validForFrom;
    }

    public Instant getValidForTo() {
        return this.validForTo;
    }

    public ProductSpecificationCharacteristic validForTo(Instant validForTo) {
        this.setValidForTo(validForTo);
        return this;
    }

    public void setValidForTo(Instant validForTo) {
        this.validForTo = validForTo;
    }

    public String getValueType() {
        return this.valueType;
    }

    public ProductSpecificationCharacteristic valueType(String valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public ProductSpecificationCharacteristic schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public ProductSpecificationCharacteristic type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueSchemaLocation() {
        return this.valueSchemaLocation;
    }

    public ProductSpecificationCharacteristic valueSchemaLocation(String valueSchemaLocation) {
        this.setValueSchemaLocation(valueSchemaLocation);
        return this;
    }

    public void setValueSchemaLocation(String valueSchemaLocation) {
        this.valueSchemaLocation = valueSchemaLocation;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ProductSpecificationCharacteristic setIsPersisted() {
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
            this.productSpecifications.forEach(i -> i.setProductSpecificationCharacteristic(null));
        }
        if (productSpecifications != null) {
            productSpecifications.forEach(i -> i.setProductSpecificationCharacteristic(this));
        }
        this.productSpecifications = productSpecifications;
    }

    public ProductSpecificationCharacteristic productSpecifications(Set<ProductSpecification> productSpecifications) {
        this.setProductSpecifications(productSpecifications);
        return this;
    }

    public ProductSpecificationCharacteristic addProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.add(productSpecification);
        productSpecification.setProductSpecificationCharacteristic(this);
        return this;
    }

    public ProductSpecificationCharacteristic removeProductSpecification(ProductSpecification productSpecification) {
        this.productSpecifications.remove(productSpecification);
        productSpecification.setProductSpecificationCharacteristic(null);
        return this;
    }

    public ProductSpecificationCharacteristicRelationship getProductSpecificationCharacteristicRelationship() {
        return this.productSpecificationCharacteristicRelationship;
    }

    public void setProductSpecificationCharacteristicRelationship(
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship
    ) {
        this.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;
    }

    public ProductSpecificationCharacteristic productSpecificationCharacteristicRelationship(
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship
    ) {
        this.setProductSpecificationCharacteristicRelationship(productSpecificationCharacteristicRelationship);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecificationCharacteristic)) {
            return false;
        }
        return id != null && id.equals(((ProductSpecificationCharacteristic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecificationCharacteristic{" +
            "id=" + getId() +
            ", configurable='" + getConfigurable() + "'" +
            ", description='" + getDescription() + "'" +
            ", extensible='" + getExtensible() + "'" +
            ", isUnique='" + getIsUnique() + "'" +
            ", maxCardinality=" + getMaxCardinality() +
            ", minCardinality=" + getMinCardinality() +
            ", name='" + getName() + "'" +
            ", regex='" + getRegex() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            ", valueSchemaLocation='" + getValueSchemaLocation() + "'" +
            "}";
    }
}
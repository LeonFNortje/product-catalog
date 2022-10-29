package network.rain.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CharacteristicValueSpecification.
 */
@Entity
@Table(name = "characteristic_value_specification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CharacteristicValueSpecification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "range_interval")
    private String rangeInterval;

    @Column(name = "regex")
    private String regex;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSpecificationCharacteristics", "characteristicValueSpecifications" }, allowSetters = true)
    private ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CharacteristicValueSpecification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public CharacteristicValueSpecification isDefault(Boolean isDefault) {
        this.setIsDefault(isDefault);
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getRangeInterval() {
        return this.rangeInterval;
    }

    public CharacteristicValueSpecification rangeInterval(String rangeInterval) {
        this.setRangeInterval(rangeInterval);
        return this;
    }

    public void setRangeInterval(String rangeInterval) {
        this.rangeInterval = rangeInterval;
    }

    public String getRegex() {
        return this.regex;
    }

    public CharacteristicValueSpecification regex(String regex) {
        this.setRegex(regex);
        return this;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public CharacteristicValueSpecification unitOfMeasure(String unitOfMeasure) {
        this.setUnitOfMeasure(unitOfMeasure);
        return this;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Instant getValidForFrom() {
        return this.validForFrom;
    }

    public CharacteristicValueSpecification validForFrom(Instant validForFrom) {
        this.setValidForFrom(validForFrom);
        return this;
    }

    public void setValidForFrom(Instant validForFrom) {
        this.validForFrom = validForFrom;
    }

    public Instant getValidForTo() {
        return this.validForTo;
    }

    public CharacteristicValueSpecification validForTo(Instant validForTo) {
        this.setValidForTo(validForTo);
        return this;
    }

    public void setValidForTo(Instant validForTo) {
        this.validForTo = validForTo;
    }

    public String getValueType() {
        return this.valueType;
    }

    public CharacteristicValueSpecification valueType(String valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public CharacteristicValueSpecification schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public CharacteristicValueSpecification type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProductSpecificationCharacteristicRelationship getProductSpecificationCharacteristicRelationship() {
        return this.productSpecificationCharacteristicRelationship;
    }

    public void setProductSpecificationCharacteristicRelationship(
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship
    ) {
        this.productSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationship;
    }

    public CharacteristicValueSpecification productSpecificationCharacteristicRelationship(
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
        if (!(o instanceof CharacteristicValueSpecification)) {
            return false;
        }
        return id != null && id.equals(((CharacteristicValueSpecification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CharacteristicValueSpecification{" +
            "id=" + getId() +
            ", isDefault='" + getIsDefault() + "'" +
            ", rangeInterval='" + getRangeInterval() + "'" +
            ", regex='" + getRegex() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

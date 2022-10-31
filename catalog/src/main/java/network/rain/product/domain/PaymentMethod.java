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
 * A PaymentMethod.
 */
@Table("payment_method")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentMethod implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private String id;

    @Column("href")
    private String href;

    @Column("authorization_code")
    private String authorizationCode;

    @Column("description")
    private String description;

    @Column("is_preferred")
    private Boolean isPreferred;

    @Column("name")
    private String name;

    @Column("status")
    private String status;

    @Column("status_date")
    private Instant statusDate;

    @Column("status_reason")
    private String statusReason;

    @Column("valid_for_from")
    private Instant validForFrom;

    @Column("valid_for_to")
    private Instant validForTo;

    @Column("schema_location")
    private String schemaLocation;

    @Column("type")
    private String type;

    @Transient
    private boolean isPersisted;

    @Transient
    @JsonIgnoreProperties(value = { "paymentMethods" }, allowSetters = true)
    private RelatedPlace relatedPlace;

    @Transient
    @JsonIgnoreProperties(value = { "paymentMethods" }, allowSetters = true)
    private RelatedParty relatedParty;

    @Column("related_place_id")
    private String relatedPlaceId;

    @Column("related_party_id")
    private String relatedPartyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PaymentMethod id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return this.href;
    }

    public PaymentMethod href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAuthorizationCode() {
        return this.authorizationCode;
    }

    public PaymentMethod authorizationCode(String authorizationCode) {
        this.setAuthorizationCode(authorizationCode);
        return this;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getDescription() {
        return this.description;
    }

    public PaymentMethod description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsPreferred() {
        return this.isPreferred;
    }

    public PaymentMethod isPreferred(Boolean isPreferred) {
        this.setIsPreferred(isPreferred);
        return this;
    }

    public void setIsPreferred(Boolean isPreferred) {
        this.isPreferred = isPreferred;
    }

    public String getName() {
        return this.name;
    }

    public PaymentMethod name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public PaymentMethod status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getStatusDate() {
        return this.statusDate;
    }

    public PaymentMethod statusDate(Instant statusDate) {
        this.setStatusDate(statusDate);
        return this;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusReason() {
        return this.statusReason;
    }

    public PaymentMethod statusReason(String statusReason) {
        this.setStatusReason(statusReason);
        return this;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Instant getValidForFrom() {
        return this.validForFrom;
    }

    public PaymentMethod validForFrom(Instant validForFrom) {
        this.setValidForFrom(validForFrom);
        return this;
    }

    public void setValidForFrom(Instant validForFrom) {
        this.validForFrom = validForFrom;
    }

    public Instant getValidForTo() {
        return this.validForTo;
    }

    public PaymentMethod validForTo(Instant validForTo) {
        this.setValidForTo(validForTo);
        return this;
    }

    public void setValidForTo(Instant validForTo) {
        this.validForTo = validForTo;
    }

    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    public PaymentMethod schemaLocation(String schemaLocation) {
        this.setSchemaLocation(schemaLocation);
        return this;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return this.type;
    }

    public PaymentMethod type(String type) {
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

    public PaymentMethod setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public RelatedPlace getRelatedPlace() {
        return this.relatedPlace;
    }

    public void setRelatedPlace(RelatedPlace relatedPlace) {
        this.relatedPlace = relatedPlace;
        this.relatedPlaceId = relatedPlace != null ? relatedPlace.getId() : null;
    }

    public PaymentMethod relatedPlace(RelatedPlace relatedPlace) {
        this.setRelatedPlace(relatedPlace);
        return this;
    }

    public RelatedParty getRelatedParty() {
        return this.relatedParty;
    }

    public void setRelatedParty(RelatedParty relatedParty) {
        this.relatedParty = relatedParty;
        this.relatedPartyId = relatedParty != null ? relatedParty.getId() : null;
    }

    public PaymentMethod relatedParty(RelatedParty relatedParty) {
        this.setRelatedParty(relatedParty);
        return this;
    }

    public String getRelatedPlaceId() {
        return this.relatedPlaceId;
    }

    public void setRelatedPlaceId(String relatedPlace) {
        this.relatedPlaceId = relatedPlace;
    }

    public String getRelatedPartyId() {
        return this.relatedPartyId;
    }

    public void setRelatedPartyId(String relatedParty) {
        this.relatedPartyId = relatedParty;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethod)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethod) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethod{" +
            "id=" + getId() +
            ", href='" + getHref() + "'" +
            ", authorizationCode='" + getAuthorizationCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isPreferred='" + getIsPreferred() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", statusReason='" + getStatusReason() + "'" +
            ", validForFrom='" + getValidForFrom() + "'" +
            ", validForTo='" + getValidForTo() + "'" +
            ", schemaLocation='" + getSchemaLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

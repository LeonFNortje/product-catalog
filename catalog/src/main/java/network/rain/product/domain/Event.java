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
 * A Event.
 */
@Table("event")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Column("correlation_id")
    private String correlationId;

    @Column("description")
    private String description;

    @Column("domain")
    private String domain;

    @Column("event_id")
    private String eventId;

    @Column("event_time")
    private Instant eventTime;

    @Column("event_type")
    private String eventType;

    @Column("href")
    private String href;

    @Id
    @Column("id")
    private String id;

    @Column("priority")
    private String priority;

    @Column("time_occurred")
    private Instant timeOccurred;

    @Column("title")
    private String title;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCorrelationId() {
        return this.correlationId;
    }

    public Event correlationId(String correlationId) {
        this.setCorrelationId(correlationId);
        return this;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomain() {
        return this.domain;
    }

    public Event domain(String domain) {
        this.setDomain(domain);
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEventId() {
        return this.eventId;
    }

    public Event eventId(String eventId) {
        this.setEventId(eventId);
        return this;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Instant getEventTime() {
        return this.eventTime;
    }

    public Event eventTime(Instant eventTime) {
        this.setEventTime(eventTime);
        return this;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return this.eventType;
    }

    public Event eventType(String eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getHref() {
        return this.href;
    }

    public Event href(String href) {
        this.setHref(href);
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return this.id;
    }

    public Event id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriority() {
        return this.priority;
    }

    public Event priority(String priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Instant getTimeOccurred() {
        return this.timeOccurred;
    }

    public Event timeOccurred(Instant timeOccurred) {
        this.setTimeOccurred(timeOccurred);
        return this;
    }

    public void setTimeOccurred(Instant timeOccurred) {
        this.timeOccurred = timeOccurred;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Event setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", correlationId='" + getCorrelationId() + "'" +
            ", description='" + getDescription() + "'" +
            ", domain='" + getDomain() + "'" +
            ", eventId='" + getEventId() + "'" +
            ", eventTime='" + getEventTime() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", href='" + getHref() + "'" +
            ", priority='" + getPriority() + "'" +
            ", timeOccurred='" + getTimeOccurred() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}

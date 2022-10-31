package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.Event;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.EventRepository;
import network.rain.product.service.dto.EventDTO;
import network.rain.product.service.mapper.EventMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link EventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class EventResourceIT {

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_EVENT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EVENT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME_OCCURRED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_OCCURRED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .correlationId(DEFAULT_CORRELATION_ID)
            .description(DEFAULT_DESCRIPTION)
            .domain(DEFAULT_DOMAIN)
            .eventId(DEFAULT_EVENT_ID)
            .eventTime(DEFAULT_EVENT_TIME)
            .eventType(DEFAULT_EVENT_TYPE)
            .href(DEFAULT_HREF)
            .priority(DEFAULT_PRIORITY)
            .timeOccurred(DEFAULT_TIME_OCCURRED)
            .title(DEFAULT_TITLE);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .correlationId(UPDATED_CORRELATION_ID)
            .description(UPDATED_DESCRIPTION)
            .domain(UPDATED_DOMAIN)
            .eventId(UPDATED_EVENT_ID)
            .eventTime(UPDATED_EVENT_TIME)
            .eventType(UPDATED_EVENT_TYPE)
            .href(UPDATED_HREF)
            .priority(UPDATED_PRIORITY)
            .timeOccurred(UPDATED_TIME_OCCURRED)
            .title(UPDATED_TITLE);
        return event;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Event.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        event = createEntity(em);
    }

    @Test
    void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().collectList().block().size();
        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getCorrelationId()).isEqualTo(DEFAULT_CORRELATION_ID);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testEvent.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testEvent.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testEvent.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testEvent.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testEvent.getTimeOccurred()).isEqualTo(DEFAULT_TIME_OCCURRED);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void createEventWithExistingId() throws Exception {
        // Create the Event with an existing ID
        event.setId("existing_id");
        EventDTO eventDTO = eventMapper.toDto(event);

        int databaseSizeBeforeCreate = eventRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEvents() {
        // Initialize the database
        event.setId(UUID.randomUUID().toString());
        eventRepository.save(event).block();

        // Get all the eventList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(event.getId()))
            .jsonPath("$.[*].correlationId")
            .value(hasItem(DEFAULT_CORRELATION_ID))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].domain")
            .value(hasItem(DEFAULT_DOMAIN))
            .jsonPath("$.[*].eventId")
            .value(hasItem(DEFAULT_EVENT_ID))
            .jsonPath("$.[*].eventTime")
            .value(hasItem(DEFAULT_EVENT_TIME.toString()))
            .jsonPath("$.[*].eventType")
            .value(hasItem(DEFAULT_EVENT_TYPE))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].priority")
            .value(hasItem(DEFAULT_PRIORITY))
            .jsonPath("$.[*].timeOccurred")
            .value(hasItem(DEFAULT_TIME_OCCURRED.toString()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE));
    }

    @Test
    void getEvent() {
        // Initialize the database
        event.setId(UUID.randomUUID().toString());
        eventRepository.save(event).block();

        // Get the event
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, event.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(event.getId()))
            .jsonPath("$.correlationId")
            .value(is(DEFAULT_CORRELATION_ID))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.domain")
            .value(is(DEFAULT_DOMAIN))
            .jsonPath("$.eventId")
            .value(is(DEFAULT_EVENT_ID))
            .jsonPath("$.eventTime")
            .value(is(DEFAULT_EVENT_TIME.toString()))
            .jsonPath("$.eventType")
            .value(is(DEFAULT_EVENT_TYPE))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.priority")
            .value(is(DEFAULT_PRIORITY))
            .jsonPath("$.timeOccurred")
            .value(is(DEFAULT_TIME_OCCURRED.toString()))
            .jsonPath("$.title")
            .value(is(DEFAULT_TITLE));
    }

    @Test
    void getNonExistingEvent() {
        // Get the event
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingEvent() throws Exception {
        // Initialize the database
        event.setId(UUID.randomUUID().toString());
        eventRepository.save(event).block();

        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).block();
        updatedEvent
            .correlationId(UPDATED_CORRELATION_ID)
            .description(UPDATED_DESCRIPTION)
            .domain(UPDATED_DOMAIN)
            .eventId(UPDATED_EVENT_ID)
            .eventTime(UPDATED_EVENT_TIME)
            .eventType(UPDATED_EVENT_TYPE)
            .href(UPDATED_HREF)
            .priority(UPDATED_PRIORITY)
            .timeOccurred(UPDATED_TIME_OCCURRED)
            .title(UPDATED_TITLE);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, eventDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getCorrelationId()).isEqualTo(UPDATED_CORRELATION_ID);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testEvent.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testEvent.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testEvent.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testEvent.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testEvent.getTimeOccurred()).isEqualTo(UPDATED_TIME_OCCURRED);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void putNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();
        event.setId(UUID.randomUUID().toString());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, eventDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();
        event.setId(UUID.randomUUID().toString());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();
        event.setId(UUID.randomUUID().toString());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEventWithPatch() throws Exception {
        // Initialize the database
        event.setId(UUID.randomUUID().toString());
        eventRepository.save(event).block();

        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent.description(UPDATED_DESCRIPTION).priority(UPDATED_PRIORITY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getCorrelationId()).isEqualTo(DEFAULT_CORRELATION_ID);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testEvent.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testEvent.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testEvent.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testEvent.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testEvent.getTimeOccurred()).isEqualTo(DEFAULT_TIME_OCCURRED);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    void fullUpdateEventWithPatch() throws Exception {
        // Initialize the database
        event.setId(UUID.randomUUID().toString());
        eventRepository.save(event).block();

        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .correlationId(UPDATED_CORRELATION_ID)
            .description(UPDATED_DESCRIPTION)
            .domain(UPDATED_DOMAIN)
            .eventId(UPDATED_EVENT_ID)
            .eventTime(UPDATED_EVENT_TIME)
            .eventType(UPDATED_EVENT_TYPE)
            .href(UPDATED_HREF)
            .priority(UPDATED_PRIORITY)
            .timeOccurred(UPDATED_TIME_OCCURRED)
            .title(UPDATED_TITLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getCorrelationId()).isEqualTo(UPDATED_CORRELATION_ID);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testEvent.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testEvent.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testEvent.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testEvent.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testEvent.getTimeOccurred()).isEqualTo(UPDATED_TIME_OCCURRED);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    void patchNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();
        event.setId(UUID.randomUUID().toString());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, eventDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();
        event.setId(UUID.randomUUID().toString());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().collectList().block().size();
        event.setId(UUID.randomUUID().toString());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(eventDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEvent() {
        // Initialize the database
        event.setId(UUID.randomUUID().toString());
        eventRepository.save(event).block();

        int databaseSizeBeforeDelete = eventRepository.findAll().collectList().block().size();

        // Delete the event
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, event.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll().collectList().block();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

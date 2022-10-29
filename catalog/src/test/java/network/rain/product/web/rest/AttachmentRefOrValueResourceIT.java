package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.AttachmentRefOrValue;
import network.rain.product.repository.AttachmentRefOrValueRepository;
import network.rain.product.repository.EntityManager;
import network.rain.product.service.dto.AttachmentRefOrValueDTO;
import network.rain.product.service.mapper.AttachmentRefOrValueMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link AttachmentRefOrValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class AttachmentRefOrValueResourceIT {

    private static final String DEFAULT_ATTACHMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE_OF_BYTES = 1;
    private static final Integer UPDATED_SIZE_OF_BYTES = 2;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FOR_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_FOR_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attachment-ref-or-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AttachmentRefOrValueRepository attachmentRefOrValueRepository;

    @Autowired
    private AttachmentRefOrValueMapper attachmentRefOrValueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private AttachmentRefOrValue attachmentRefOrValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentRefOrValue createEntity(EntityManager em) {
        AttachmentRefOrValue attachmentRefOrValue = new AttachmentRefOrValue()
            .attachmentType(DEFAULT_ATTACHMENT_TYPE)
            .content(DEFAULT_CONTENT)
            .description(DEFAULT_DESCRIPTION)
            .href(DEFAULT_HREF)
            .mimeType(DEFAULT_MIME_TYPE)
            .name(DEFAULT_NAME)
            .sizeOfBytes(DEFAULT_SIZE_OF_BYTES)
            .url(DEFAULT_URL)
            .validForFrom(DEFAULT_VALID_FOR_FROM)
            .validForTo(DEFAULT_VALID_FOR_TO)
            .valueType(DEFAULT_VALUE_TYPE)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return attachmentRefOrValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttachmentRefOrValue createUpdatedEntity(EntityManager em) {
        AttachmentRefOrValue attachmentRefOrValue = new AttachmentRefOrValue()
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .content(UPDATED_CONTENT)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .mimeType(UPDATED_MIME_TYPE)
            .name(UPDATED_NAME)
            .sizeOfBytes(UPDATED_SIZE_OF_BYTES)
            .url(UPDATED_URL)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return attachmentRefOrValue;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(AttachmentRefOrValue.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        attachmentRefOrValue = createEntity(em);
    }

    @Test
    void createAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeCreate = attachmentRefOrValueRepository.findAll().collectList().block().size();
        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeCreate + 1);
        AttachmentRefOrValue testAttachmentRefOrValue = attachmentRefOrValueList.get(attachmentRefOrValueList.size() - 1);
        assertThat(testAttachmentRefOrValue.getAttachmentType()).isEqualTo(DEFAULT_ATTACHMENT_TYPE);
        assertThat(testAttachmentRefOrValue.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAttachmentRefOrValue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAttachmentRefOrValue.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testAttachmentRefOrValue.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testAttachmentRefOrValue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAttachmentRefOrValue.getSizeOfBytes()).isEqualTo(DEFAULT_SIZE_OF_BYTES);
        assertThat(testAttachmentRefOrValue.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAttachmentRefOrValue.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testAttachmentRefOrValue.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testAttachmentRefOrValue.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testAttachmentRefOrValue.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testAttachmentRefOrValue.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createAttachmentRefOrValueWithExistingId() throws Exception {
        // Create the AttachmentRefOrValue with an existing ID
        attachmentRefOrValue.setId("existing_id");
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        int databaseSizeBeforeCreate = attachmentRefOrValueRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAttachmentRefOrValues() {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.save(attachmentRefOrValue).block();

        // Get all the attachmentRefOrValueList
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
            .value(hasItem(attachmentRefOrValue.getId()))
            .jsonPath("$.[*].attachmentType")
            .value(hasItem(DEFAULT_ATTACHMENT_TYPE))
            .jsonPath("$.[*].content")
            .value(hasItem(DEFAULT_CONTENT))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].mimeType")
            .value(hasItem(DEFAULT_MIME_TYPE))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].sizeOfBytes")
            .value(hasItem(DEFAULT_SIZE_OF_BYTES))
            .jsonPath("$.[*].url")
            .value(hasItem(DEFAULT_URL))
            .jsonPath("$.[*].validForFrom")
            .value(hasItem(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.[*].validForTo")
            .value(hasItem(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.[*].valueType")
            .value(hasItem(DEFAULT_VALUE_TYPE))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getAttachmentRefOrValue() {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.save(attachmentRefOrValue).block();

        // Get the attachmentRefOrValue
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, attachmentRefOrValue.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(attachmentRefOrValue.getId()))
            .jsonPath("$.attachmentType")
            .value(is(DEFAULT_ATTACHMENT_TYPE))
            .jsonPath("$.content")
            .value(is(DEFAULT_CONTENT))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.mimeType")
            .value(is(DEFAULT_MIME_TYPE))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.sizeOfBytes")
            .value(is(DEFAULT_SIZE_OF_BYTES))
            .jsonPath("$.url")
            .value(is(DEFAULT_URL))
            .jsonPath("$.validForFrom")
            .value(is(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.validForTo")
            .value(is(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.valueType")
            .value(is(DEFAULT_VALUE_TYPE))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingAttachmentRefOrValue() {
        // Get the attachmentRefOrValue
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingAttachmentRefOrValue() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.save(attachmentRefOrValue).block();

        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();

        // Update the attachmentRefOrValue
        AttachmentRefOrValue updatedAttachmentRefOrValue = attachmentRefOrValueRepository.findById(attachmentRefOrValue.getId()).block();
        updatedAttachmentRefOrValue
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .content(UPDATED_CONTENT)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .mimeType(UPDATED_MIME_TYPE)
            .name(UPDATED_NAME)
            .sizeOfBytes(UPDATED_SIZE_OF_BYTES)
            .url(UPDATED_URL)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(updatedAttachmentRefOrValue);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, attachmentRefOrValueDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
        AttachmentRefOrValue testAttachmentRefOrValue = attachmentRefOrValueList.get(attachmentRefOrValueList.size() - 1);
        assertThat(testAttachmentRefOrValue.getAttachmentType()).isEqualTo(UPDATED_ATTACHMENT_TYPE);
        assertThat(testAttachmentRefOrValue.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAttachmentRefOrValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAttachmentRefOrValue.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testAttachmentRefOrValue.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testAttachmentRefOrValue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAttachmentRefOrValue.getSizeOfBytes()).isEqualTo(UPDATED_SIZE_OF_BYTES);
        assertThat(testAttachmentRefOrValue.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAttachmentRefOrValue.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testAttachmentRefOrValue.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testAttachmentRefOrValue.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testAttachmentRefOrValue.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testAttachmentRefOrValue.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, attachmentRefOrValueDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAttachmentRefOrValueWithPatch() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.save(attachmentRefOrValue).block();

        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();

        // Update the attachmentRefOrValue using partial update
        AttachmentRefOrValue partialUpdatedAttachmentRefOrValue = new AttachmentRefOrValue();
        partialUpdatedAttachmentRefOrValue.setId(attachmentRefOrValue.getId());

        partialUpdatedAttachmentRefOrValue
            .content(UPDATED_CONTENT)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .sizeOfBytes(UPDATED_SIZE_OF_BYTES)
            .url(UPDATED_URL)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAttachmentRefOrValue.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachmentRefOrValue))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
        AttachmentRefOrValue testAttachmentRefOrValue = attachmentRefOrValueList.get(attachmentRefOrValueList.size() - 1);
        assertThat(testAttachmentRefOrValue.getAttachmentType()).isEqualTo(DEFAULT_ATTACHMENT_TYPE);
        assertThat(testAttachmentRefOrValue.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAttachmentRefOrValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAttachmentRefOrValue.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testAttachmentRefOrValue.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testAttachmentRefOrValue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAttachmentRefOrValue.getSizeOfBytes()).isEqualTo(UPDATED_SIZE_OF_BYTES);
        assertThat(testAttachmentRefOrValue.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAttachmentRefOrValue.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testAttachmentRefOrValue.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testAttachmentRefOrValue.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testAttachmentRefOrValue.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testAttachmentRefOrValue.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateAttachmentRefOrValueWithPatch() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.save(attachmentRefOrValue).block();

        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();

        // Update the attachmentRefOrValue using partial update
        AttachmentRefOrValue partialUpdatedAttachmentRefOrValue = new AttachmentRefOrValue();
        partialUpdatedAttachmentRefOrValue.setId(attachmentRefOrValue.getId());

        partialUpdatedAttachmentRefOrValue
            .attachmentType(UPDATED_ATTACHMENT_TYPE)
            .content(UPDATED_CONTENT)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .mimeType(UPDATED_MIME_TYPE)
            .name(UPDATED_NAME)
            .sizeOfBytes(UPDATED_SIZE_OF_BYTES)
            .url(UPDATED_URL)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAttachmentRefOrValue.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachmentRefOrValue))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
        AttachmentRefOrValue testAttachmentRefOrValue = attachmentRefOrValueList.get(attachmentRefOrValueList.size() - 1);
        assertThat(testAttachmentRefOrValue.getAttachmentType()).isEqualTo(UPDATED_ATTACHMENT_TYPE);
        assertThat(testAttachmentRefOrValue.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAttachmentRefOrValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAttachmentRefOrValue.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testAttachmentRefOrValue.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testAttachmentRefOrValue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAttachmentRefOrValue.getSizeOfBytes()).isEqualTo(UPDATED_SIZE_OF_BYTES);
        assertThat(testAttachmentRefOrValue.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAttachmentRefOrValue.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testAttachmentRefOrValue.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testAttachmentRefOrValue.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testAttachmentRefOrValue.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testAttachmentRefOrValue.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, attachmentRefOrValueDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().collectList().block().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAttachmentRefOrValue() {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.save(attachmentRefOrValue).block();

        int databaseSizeBeforeDelete = attachmentRefOrValueRepository.findAll().collectList().block().size();

        // Delete the attachmentRefOrValue
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, attachmentRefOrValue.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll().collectList().block();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

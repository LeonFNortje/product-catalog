package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.AttachmentRefOrValue;
import network.rain.product.repository.AttachmentRefOrValueRepository;
import network.rain.product.service.dto.AttachmentRefOrValueDTO;
import network.rain.product.service.mapper.AttachmentRefOrValueMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AttachmentRefOrValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restAttachmentRefOrValueMockMvc;

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

    @BeforeEach
    public void initTest() {
        attachmentRefOrValue = createEntity(em);
    }

    @Test
    @Transactional
    void createAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeCreate = attachmentRefOrValueRepository.findAll().size();
        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);
        restAttachmentRefOrValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
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
    @Transactional
    void createAttachmentRefOrValueWithExistingId() throws Exception {
        // Create the AttachmentRefOrValue with an existing ID
        attachmentRefOrValue.setId("existing_id");
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        int databaseSizeBeforeCreate = attachmentRefOrValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentRefOrValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttachmentRefOrValues() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.saveAndFlush(attachmentRefOrValue);

        // Get all the attachmentRefOrValueList
        restAttachmentRefOrValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentRefOrValue.getId())))
            .andExpect(jsonPath("$.[*].attachmentType").value(hasItem(DEFAULT_ATTACHMENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sizeOfBytes").value(hasItem(DEFAULT_SIZE_OF_BYTES)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].validForFrom").value(hasItem(DEFAULT_VALID_FOR_FROM.toString())))
            .andExpect(jsonPath("$.[*].validForTo").value(hasItem(DEFAULT_VALID_FOR_TO.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getAttachmentRefOrValue() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.saveAndFlush(attachmentRefOrValue);

        // Get the attachmentRefOrValue
        restAttachmentRefOrValueMockMvc
            .perform(get(ENTITY_API_URL_ID, attachmentRefOrValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachmentRefOrValue.getId()))
            .andExpect(jsonPath("$.attachmentType").value(DEFAULT_ATTACHMENT_TYPE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sizeOfBytes").value(DEFAULT_SIZE_OF_BYTES))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.validForFrom").value(DEFAULT_VALID_FOR_FROM.toString()))
            .andExpect(jsonPath("$.validForTo").value(DEFAULT_VALID_FOR_TO.toString()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingAttachmentRefOrValue() throws Exception {
        // Get the attachmentRefOrValue
        restAttachmentRefOrValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttachmentRefOrValue() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.saveAndFlush(attachmentRefOrValue);

        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();

        // Update the attachmentRefOrValue
        AttachmentRefOrValue updatedAttachmentRefOrValue = attachmentRefOrValueRepository.findById(attachmentRefOrValue.getId()).get();
        // Disconnect from session so that the updates on updatedAttachmentRefOrValue are not directly saved in db
        em.detach(updatedAttachmentRefOrValue);
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

        restAttachmentRefOrValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachmentRefOrValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
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
    @Transactional
    void putNonExistingAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentRefOrValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attachmentRefOrValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentRefOrValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentRefOrValueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttachmentRefOrValueWithPatch() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.saveAndFlush(attachmentRefOrValue);

        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();

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

        restAttachmentRefOrValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachmentRefOrValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachmentRefOrValue))
            )
            .andExpect(status().isOk());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
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
    @Transactional
    void fullUpdateAttachmentRefOrValueWithPatch() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.saveAndFlush(attachmentRefOrValue);

        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();

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

        restAttachmentRefOrValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttachmentRefOrValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttachmentRefOrValue))
            )
            .andExpect(status().isOk());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
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
    @Transactional
    void patchNonExistingAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentRefOrValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attachmentRefOrValueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentRefOrValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttachmentRefOrValue() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRefOrValueRepository.findAll().size();
        attachmentRefOrValue.setId(UUID.randomUUID().toString());

        // Create the AttachmentRefOrValue
        AttachmentRefOrValueDTO attachmentRefOrValueDTO = attachmentRefOrValueMapper.toDto(attachmentRefOrValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttachmentRefOrValueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attachmentRefOrValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttachmentRefOrValue in the database
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttachmentRefOrValue() throws Exception {
        // Initialize the database
        attachmentRefOrValue.setId(UUID.randomUUID().toString());
        attachmentRefOrValueRepository.saveAndFlush(attachmentRefOrValue);

        int databaseSizeBeforeDelete = attachmentRefOrValueRepository.findAll().size();

        // Delete the attachmentRefOrValue
        restAttachmentRefOrValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, attachmentRefOrValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttachmentRefOrValue> attachmentRefOrValueList = attachmentRefOrValueRepository.findAll();
        assertThat(attachmentRefOrValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

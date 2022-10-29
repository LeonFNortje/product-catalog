package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.BundledProductSpecification;
import network.rain.product.repository.BundledProductSpecificationRepository;
import network.rain.product.service.dto.BundledProductSpecificationDTO;
import network.rain.product.service.mapper.BundledProductSpecificationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BundledProductSpecificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BundledProductSpecificationResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LIFECYCLE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_LIFECYCLE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bundled-product-specifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BundledProductSpecificationRepository bundledProductSpecificationRepository;

    @Autowired
    private BundledProductSpecificationMapper bundledProductSpecificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBundledProductSpecificationMockMvc;

    private BundledProductSpecification bundledProductSpecification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BundledProductSpecification createEntity(EntityManager em) {
        BundledProductSpecification bundledProductSpecification = new BundledProductSpecification()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .lifecycleStatus(DEFAULT_LIFECYCLE_STATUS)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return bundledProductSpecification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BundledProductSpecification createUpdatedEntity(EntityManager em) {
        BundledProductSpecification bundledProductSpecification = new BundledProductSpecification()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return bundledProductSpecification;
    }

    @BeforeEach
    public void initTest() {
        bundledProductSpecification = createEntity(em);
    }

    @Test
    @Transactional
    void createBundledProductSpecification() throws Exception {
        int databaseSizeBeforeCreate = bundledProductSpecificationRepository.findAll().size();
        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );
        restBundledProductSpecificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeCreate + 1);
        BundledProductSpecification testBundledProductSpecification = bundledProductSpecificationList.get(
            bundledProductSpecificationList.size() - 1
        );
        assertThat(testBundledProductSpecification.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testBundledProductSpecification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBundledProductSpecification.getLifecycleStatus()).isEqualTo(DEFAULT_LIFECYCLE_STATUS);
        assertThat(testBundledProductSpecification.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testBundledProductSpecification.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createBundledProductSpecificationWithExistingId() throws Exception {
        // Create the BundledProductSpecification with an existing ID
        bundledProductSpecification.setId("existing_id");
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        int databaseSizeBeforeCreate = bundledProductSpecificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBundledProductSpecificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBundledProductSpecifications() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.saveAndFlush(bundledProductSpecification);

        // Get all the bundledProductSpecificationList
        restBundledProductSpecificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bundledProductSpecification.getId())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lifecycleStatus").value(hasItem(DEFAULT_LIFECYCLE_STATUS)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getBundledProductSpecification() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.saveAndFlush(bundledProductSpecification);

        // Get the bundledProductSpecification
        restBundledProductSpecificationMockMvc
            .perform(get(ENTITY_API_URL_ID, bundledProductSpecification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bundledProductSpecification.getId()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lifecycleStatus").value(DEFAULT_LIFECYCLE_STATUS))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingBundledProductSpecification() throws Exception {
        // Get the bundledProductSpecification
        restBundledProductSpecificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBundledProductSpecification() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.saveAndFlush(bundledProductSpecification);

        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();

        // Update the bundledProductSpecification
        BundledProductSpecification updatedBundledProductSpecification = bundledProductSpecificationRepository
            .findById(bundledProductSpecification.getId())
            .get();
        // Disconnect from session so that the updates on updatedBundledProductSpecification are not directly saved in db
        em.detach(updatedBundledProductSpecification);
        updatedBundledProductSpecification
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            updatedBundledProductSpecification
        );

        restBundledProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bundledProductSpecificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
        BundledProductSpecification testBundledProductSpecification = bundledProductSpecificationList.get(
            bundledProductSpecificationList.size() - 1
        );
        assertThat(testBundledProductSpecification.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testBundledProductSpecification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBundledProductSpecification.getLifecycleStatus()).isEqualTo(UPDATED_LIFECYCLE_STATUS);
        assertThat(testBundledProductSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testBundledProductSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBundledProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bundledProductSpecificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBundledProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBundledProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBundledProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.saveAndFlush(bundledProductSpecification);

        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();

        // Update the bundledProductSpecification using partial update
        BundledProductSpecification partialUpdatedBundledProductSpecification = new BundledProductSpecification();
        partialUpdatedBundledProductSpecification.setId(bundledProductSpecification.getId());

        partialUpdatedBundledProductSpecification
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        restBundledProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBundledProductSpecification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBundledProductSpecification))
            )
            .andExpect(status().isOk());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
        BundledProductSpecification testBundledProductSpecification = bundledProductSpecificationList.get(
            bundledProductSpecificationList.size() - 1
        );
        assertThat(testBundledProductSpecification.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testBundledProductSpecification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBundledProductSpecification.getLifecycleStatus()).isEqualTo(UPDATED_LIFECYCLE_STATUS);
        assertThat(testBundledProductSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testBundledProductSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateBundledProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.saveAndFlush(bundledProductSpecification);

        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();

        // Update the bundledProductSpecification using partial update
        BundledProductSpecification partialUpdatedBundledProductSpecification = new BundledProductSpecification();
        partialUpdatedBundledProductSpecification.setId(bundledProductSpecification.getId());

        partialUpdatedBundledProductSpecification
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        restBundledProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBundledProductSpecification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBundledProductSpecification))
            )
            .andExpect(status().isOk());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
        BundledProductSpecification testBundledProductSpecification = bundledProductSpecificationList.get(
            bundledProductSpecificationList.size() - 1
        );
        assertThat(testBundledProductSpecification.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testBundledProductSpecification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBundledProductSpecification.getLifecycleStatus()).isEqualTo(UPDATED_LIFECYCLE_STATUS);
        assertThat(testBundledProductSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testBundledProductSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBundledProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bundledProductSpecificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBundledProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBundledProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBundledProductSpecification() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.saveAndFlush(bundledProductSpecification);

        int databaseSizeBeforeDelete = bundledProductSpecificationRepository.findAll().size();

        // Delete the bundledProductSpecification
        restBundledProductSpecificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, bundledProductSpecification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository.findAll();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

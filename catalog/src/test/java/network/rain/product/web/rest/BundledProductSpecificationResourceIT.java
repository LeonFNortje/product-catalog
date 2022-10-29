package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.BundledProductSpecification;
import network.rain.product.repository.BundledProductSpecificationRepository;
import network.rain.product.repository.EntityManager;
import network.rain.product.service.dto.BundledProductSpecificationDTO;
import network.rain.product.service.mapper.BundledProductSpecificationMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link BundledProductSpecificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(BundledProductSpecification.class).block();
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
        bundledProductSpecification = createEntity(em);
    }

    @Test
    void createBundledProductSpecification() throws Exception {
        int databaseSizeBeforeCreate = bundledProductSpecificationRepository.findAll().collectList().block().size();
        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void createBundledProductSpecificationWithExistingId() throws Exception {
        // Create the BundledProductSpecification with an existing ID
        bundledProductSpecification.setId("existing_id");
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        int databaseSizeBeforeCreate = bundledProductSpecificationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllBundledProductSpecifications() {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.save(bundledProductSpecification).block();

        // Get all the bundledProductSpecificationList
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
            .value(hasItem(bundledProductSpecification.getId()))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].lifecycleStatus")
            .value(hasItem(DEFAULT_LIFECYCLE_STATUS))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getBundledProductSpecification() {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.save(bundledProductSpecification).block();

        // Get the bundledProductSpecification
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, bundledProductSpecification.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(bundledProductSpecification.getId()))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.lifecycleStatus")
            .value(is(DEFAULT_LIFECYCLE_STATUS))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingBundledProductSpecification() {
        // Get the bundledProductSpecification
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingBundledProductSpecification() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.save(bundledProductSpecification).block();

        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();

        // Update the bundledProductSpecification
        BundledProductSpecification updatedBundledProductSpecification = bundledProductSpecificationRepository
            .findById(bundledProductSpecification.getId())
            .block();
        updatedBundledProductSpecification
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            updatedBundledProductSpecification
        );

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, bundledProductSpecificationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void putNonExistingBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, bundledProductSpecificationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBundledProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.save(bundledProductSpecification).block();

        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();

        // Update the bundledProductSpecification using partial update
        BundledProductSpecification partialUpdatedBundledProductSpecification = new BundledProductSpecification();
        partialUpdatedBundledProductSpecification.setId(bundledProductSpecification.getId());

        partialUpdatedBundledProductSpecification
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedBundledProductSpecification.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedBundledProductSpecification))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void fullUpdateBundledProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.save(bundledProductSpecification).block();

        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();

        // Update the bundledProductSpecification using partial update
        BundledProductSpecification partialUpdatedBundledProductSpecification = new BundledProductSpecification();
        partialUpdatedBundledProductSpecification.setId(bundledProductSpecification.getId());

        partialUpdatedBundledProductSpecification
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedBundledProductSpecification.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedBundledProductSpecification))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void patchNonExistingBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, bundledProductSpecificationDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBundledProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = bundledProductSpecificationRepository.findAll().collectList().block().size();
        bundledProductSpecification.setId(UUID.randomUUID().toString());

        // Create the BundledProductSpecification
        BundledProductSpecificationDTO bundledProductSpecificationDTO = bundledProductSpecificationMapper.toDto(
            bundledProductSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(bundledProductSpecificationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the BundledProductSpecification in the database
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBundledProductSpecification() {
        // Initialize the database
        bundledProductSpecification.setId(UUID.randomUUID().toString());
        bundledProductSpecificationRepository.save(bundledProductSpecification).block();

        int databaseSizeBeforeDelete = bundledProductSpecificationRepository.findAll().collectList().block().size();

        // Delete the bundledProductSpecification
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, bundledProductSpecification.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<BundledProductSpecification> bundledProductSpecificationList = bundledProductSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(bundledProductSpecificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

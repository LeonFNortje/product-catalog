package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.ServiceSpecificationRef;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.ServiceSpecificationRefRepository;
import network.rain.product.service.dto.ServiceSpecificationRefDTO;
import network.rain.product.service.mapper.ServiceSpecificationRefMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ServiceSpecificationRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ServiceSpecificationRefResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/service-specification-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ServiceSpecificationRefRepository serviceSpecificationRefRepository;

    @Autowired
    private ServiceSpecificationRefMapper serviceSpecificationRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ServiceSpecificationRef serviceSpecificationRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSpecificationRef createEntity(EntityManager em) {
        ServiceSpecificationRef serviceSpecificationRef = new ServiceSpecificationRef()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return serviceSpecificationRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSpecificationRef createUpdatedEntity(EntityManager em) {
        ServiceSpecificationRef serviceSpecificationRef = new ServiceSpecificationRef()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return serviceSpecificationRef;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ServiceSpecificationRef.class).block();
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
        serviceSpecificationRef = createEntity(em);
    }

    @Test
    void createServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeCreate = serviceSpecificationRefRepository.findAll().collectList().block().size();
        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createServiceSpecificationRefWithExistingId() throws Exception {
        // Create the ServiceSpecificationRef with an existing ID
        serviceSpecificationRef.setId("existing_id");
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        int databaseSizeBeforeCreate = serviceSpecificationRefRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllServiceSpecificationRefs() {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.save(serviceSpecificationRef).block();

        // Get all the serviceSpecificationRefList
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
            .value(hasItem(serviceSpecificationRef.getId()))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].version")
            .value(hasItem(DEFAULT_VERSION))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getServiceSpecificationRef() {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.save(serviceSpecificationRef).block();

        // Get the serviceSpecificationRef
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, serviceSpecificationRef.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(serviceSpecificationRef.getId()))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.version")
            .value(is(DEFAULT_VERSION))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingServiceSpecificationRef() {
        // Get the serviceSpecificationRef
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingServiceSpecificationRef() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.save(serviceSpecificationRef).block();

        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();

        // Update the serviceSpecificationRef
        ServiceSpecificationRef updatedServiceSpecificationRef = serviceSpecificationRefRepository
            .findById(serviceSpecificationRef.getId())
            .block();
        updatedServiceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(updatedServiceSpecificationRef);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, serviceSpecificationRefDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, serviceSpecificationRefDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateServiceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.save(serviceSpecificationRef).block();

        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();

        // Update the serviceSpecificationRef using partial update
        ServiceSpecificationRef partialUpdatedServiceSpecificationRef = new ServiceSpecificationRef();
        partialUpdatedServiceSpecificationRef.setId(serviceSpecificationRef.getId());

        partialUpdatedServiceSpecificationRef.version(UPDATED_VERSION).schemaLocation(UPDATED_SCHEMA_LOCATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedServiceSpecificationRef.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceSpecificationRef))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateServiceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.save(serviceSpecificationRef).block();

        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();

        // Update the serviceSpecificationRef using partial update
        ServiceSpecificationRef partialUpdatedServiceSpecificationRef = new ServiceSpecificationRef();
        partialUpdatedServiceSpecificationRef.setId(serviceSpecificationRef.getId());

        partialUpdatedServiceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedServiceSpecificationRef.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceSpecificationRef))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, serviceSpecificationRefDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().collectList().block().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteServiceSpecificationRef() {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.save(serviceSpecificationRef).block();

        int databaseSizeBeforeDelete = serviceSpecificationRefRepository.findAll().collectList().block().size();

        // Delete the serviceSpecificationRef
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, serviceSpecificationRef.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll().collectList().block();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

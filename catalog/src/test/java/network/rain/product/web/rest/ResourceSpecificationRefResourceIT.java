package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.ResourceSpecificationRef;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.ResourceSpecificationRefRepository;
import network.rain.product.service.dto.ResourceSpecificationRefDTO;
import network.rain.product.service.mapper.ResourceSpecificationRefMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ResourceSpecificationRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ResourceSpecificationRefResourceIT {

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

    private static final String ENTITY_API_URL = "/api/resource-specification-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ResourceSpecificationRefRepository resourceSpecificationRefRepository;

    @Autowired
    private ResourceSpecificationRefMapper resourceSpecificationRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ResourceSpecificationRef resourceSpecificationRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceSpecificationRef createEntity(EntityManager em) {
        ResourceSpecificationRef resourceSpecificationRef = new ResourceSpecificationRef()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return resourceSpecificationRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceSpecificationRef createUpdatedEntity(EntityManager em) {
        ResourceSpecificationRef resourceSpecificationRef = new ResourceSpecificationRef()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return resourceSpecificationRef;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ResourceSpecificationRef.class).block();
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
        resourceSpecificationRef = createEntity(em);
    }

    @Test
    void createResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeCreate = resourceSpecificationRefRepository.findAll().collectList().block().size();
        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createResourceSpecificationRefWithExistingId() throws Exception {
        // Create the ResourceSpecificationRef with an existing ID
        resourceSpecificationRef.setId("existing_id");
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        int databaseSizeBeforeCreate = resourceSpecificationRefRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllResourceSpecificationRefs() {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.save(resourceSpecificationRef).block();

        // Get all the resourceSpecificationRefList
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
            .value(hasItem(resourceSpecificationRef.getId()))
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
    void getResourceSpecificationRef() {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.save(resourceSpecificationRef).block();

        // Get the resourceSpecificationRef
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, resourceSpecificationRef.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(resourceSpecificationRef.getId()))
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
    void getNonExistingResourceSpecificationRef() {
        // Get the resourceSpecificationRef
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingResourceSpecificationRef() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.save(resourceSpecificationRef).block();

        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();

        // Update the resourceSpecificationRef
        ResourceSpecificationRef updatedResourceSpecificationRef = resourceSpecificationRefRepository
            .findById(resourceSpecificationRef.getId())
            .block();
        updatedResourceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(updatedResourceSpecificationRef);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, resourceSpecificationRefDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, resourceSpecificationRefDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateResourceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.save(resourceSpecificationRef).block();

        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();

        // Update the resourceSpecificationRef using partial update
        ResourceSpecificationRef partialUpdatedResourceSpecificationRef = new ResourceSpecificationRef();
        partialUpdatedResourceSpecificationRef.setId(resourceSpecificationRef.getId());

        partialUpdatedResourceSpecificationRef.name(UPDATED_NAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedResourceSpecificationRef.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceSpecificationRef))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateResourceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.save(resourceSpecificationRef).block();

        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();

        // Update the resourceSpecificationRef using partial update
        ResourceSpecificationRef partialUpdatedResourceSpecificationRef = new ResourceSpecificationRef();
        partialUpdatedResourceSpecificationRef.setId(resourceSpecificationRef.getId());

        partialUpdatedResourceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedResourceSpecificationRef.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceSpecificationRef))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, resourceSpecificationRefDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().collectList().block().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteResourceSpecificationRef() {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.save(resourceSpecificationRef).block();

        int databaseSizeBeforeDelete = resourceSpecificationRefRepository.findAll().collectList().block().size();

        // Delete the resourceSpecificationRef
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, resourceSpecificationRef.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll().collectList().block();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

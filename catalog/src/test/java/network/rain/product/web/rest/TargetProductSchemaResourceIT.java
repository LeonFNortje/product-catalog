package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.TargetProductSchema;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.TargetProductSchemaRepository;
import network.rain.product.service.dto.TargetProductSchemaDTO;
import network.rain.product.service.mapper.TargetProductSchemaMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link TargetProductSchemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TargetProductSchemaResourceIT {

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/target-product-schemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TargetProductSchemaRepository targetProductSchemaRepository;

    @Autowired
    private TargetProductSchemaMapper targetProductSchemaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private TargetProductSchema targetProductSchema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetProductSchema createEntity(EntityManager em) {
        TargetProductSchema targetProductSchema = new TargetProductSchema().schemaLocation(DEFAULT_SCHEMA_LOCATION).type(DEFAULT_TYPE);
        return targetProductSchema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetProductSchema createUpdatedEntity(EntityManager em) {
        TargetProductSchema targetProductSchema = new TargetProductSchema().schemaLocation(UPDATED_SCHEMA_LOCATION).type(UPDATED_TYPE);
        return targetProductSchema;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(TargetProductSchema.class).block();
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
        targetProductSchema = createEntity(em);
    }

    @Test
    void createTargetProductSchema() throws Exception {
        int databaseSizeBeforeCreate = targetProductSchemaRepository.findAll().collectList().block().size();
        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeCreate + 1);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createTargetProductSchemaWithExistingId() throws Exception {
        // Create the TargetProductSchema with an existing ID
        targetProductSchema.setId(1L);
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        int databaseSizeBeforeCreate = targetProductSchemaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTargetProductSchemas() {
        // Initialize the database
        targetProductSchemaRepository.save(targetProductSchema).block();

        // Get all the targetProductSchemaList
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
            .value(hasItem(targetProductSchema.getId().intValue()))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getTargetProductSchema() {
        // Initialize the database
        targetProductSchemaRepository.save(targetProductSchema).block();

        // Get the targetProductSchema
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, targetProductSchema.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(targetProductSchema.getId().intValue()))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingTargetProductSchema() {
        // Get the targetProductSchema
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTargetProductSchema() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.save(targetProductSchema).block();

        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();

        // Update the targetProductSchema
        TargetProductSchema updatedTargetProductSchema = targetProductSchemaRepository.findById(targetProductSchema.getId()).block();
        updatedTargetProductSchema.schemaLocation(UPDATED_SCHEMA_LOCATION).type(UPDATED_TYPE);
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(updatedTargetProductSchema);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, targetProductSchemaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, targetProductSchemaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTargetProductSchemaWithPatch() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.save(targetProductSchema).block();

        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();

        // Update the targetProductSchema using partial update
        TargetProductSchema partialUpdatedTargetProductSchema = new TargetProductSchema();
        partialUpdatedTargetProductSchema.setId(targetProductSchema.getId());

        partialUpdatedTargetProductSchema.schemaLocation(UPDATED_SCHEMA_LOCATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTargetProductSchema.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetProductSchema))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateTargetProductSchemaWithPatch() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.save(targetProductSchema).block();

        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();

        // Update the targetProductSchema using partial update
        TargetProductSchema partialUpdatedTargetProductSchema = new TargetProductSchema();
        partialUpdatedTargetProductSchema.setId(targetProductSchema.getId());

        partialUpdatedTargetProductSchema.schemaLocation(UPDATED_SCHEMA_LOCATION).type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTargetProductSchema.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetProductSchema))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, targetProductSchemaDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().collectList().block().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTargetProductSchema() {
        // Initialize the database
        targetProductSchemaRepository.save(targetProductSchema).block();

        int databaseSizeBeforeDelete = targetProductSchemaRepository.findAll().collectList().block().size();

        // Delete the targetProductSchema
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, targetProductSchema.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll().collectList().block();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

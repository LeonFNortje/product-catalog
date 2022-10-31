package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.RelatedPlace;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.RelatedPlaceRepository;
import network.rain.product.service.dto.RelatedPlaceDTO;
import network.rain.product.service.mapper.RelatedPlaceMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link RelatedPlaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RelatedPlaceResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/related-places";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RelatedPlaceRepository relatedPlaceRepository;

    @Autowired
    private RelatedPlaceMapper relatedPlaceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private RelatedPlace relatedPlace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedPlace createEntity(EntityManager em) {
        RelatedPlace relatedPlace = new RelatedPlace()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .role(DEFAULT_ROLE)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return relatedPlace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedPlace createUpdatedEntity(EntityManager em) {
        RelatedPlace relatedPlace = new RelatedPlace()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return relatedPlace;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(RelatedPlace.class).block();
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
        relatedPlace = createEntity(em);
    }

    @Test
    void createRelatedPlace() throws Exception {
        int databaseSizeBeforeCreate = relatedPlaceRepository.findAll().collectList().block().size();
        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createRelatedPlaceWithExistingId() throws Exception {
        // Create the RelatedPlace with an existing ID
        relatedPlace.setId("existing_id");
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        int databaseSizeBeforeCreate = relatedPlaceRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRelatedPlaces() {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.save(relatedPlace).block();

        // Get all the relatedPlaceList
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
            .value(hasItem(relatedPlace.getId()))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].role")
            .value(hasItem(DEFAULT_ROLE))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getRelatedPlace() {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.save(relatedPlace).block();

        // Get the relatedPlace
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, relatedPlace.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(relatedPlace.getId()))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.role")
            .value(is(DEFAULT_ROLE))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingRelatedPlace() {
        // Get the relatedPlace
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRelatedPlace() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.save(relatedPlace).block();

        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();

        // Update the relatedPlace
        RelatedPlace updatedRelatedPlace = relatedPlaceRepository.findById(relatedPlace.getId()).block();
        updatedRelatedPlace
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(updatedRelatedPlace);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, relatedPlaceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, relatedPlaceDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRelatedPlaceWithPatch() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.save(relatedPlace).block();

        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();

        // Update the relatedPlace using partial update
        RelatedPlace partialUpdatedRelatedPlace = new RelatedPlace();
        partialUpdatedRelatedPlace.setId(relatedPlace.getId());

        partialUpdatedRelatedPlace.name(UPDATED_NAME).role(UPDATED_ROLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRelatedPlace.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedPlace))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateRelatedPlaceWithPatch() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.save(relatedPlace).block();

        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();

        // Update the relatedPlace using partial update
        RelatedPlace partialUpdatedRelatedPlace = new RelatedPlace();
        partialUpdatedRelatedPlace.setId(relatedPlace.getId());

        partialUpdatedRelatedPlace
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRelatedPlace.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedPlace))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, relatedPlaceDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().collectList().block().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRelatedPlace() {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.save(relatedPlace).block();

        int databaseSizeBeforeDelete = relatedPlaceRepository.findAll().collectList().block().size();

        // Delete the relatedPlace
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, relatedPlace.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll().collectList().block();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

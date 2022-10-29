package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.RelatedParty;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.RelatedPartyRepository;
import network.rain.product.service.dto.RelatedPartyDTO;
import network.rain.product.service.mapper.RelatedPartyMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link RelatedPartyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RelatedPartyResourceIT {

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

    private static final String ENTITY_API_URL = "/api/related-parties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RelatedPartyRepository relatedPartyRepository;

    @Autowired
    private RelatedPartyMapper relatedPartyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private RelatedParty relatedParty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedParty createEntity(EntityManager em) {
        RelatedParty relatedParty = new RelatedParty()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .role(DEFAULT_ROLE)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return relatedParty;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedParty createUpdatedEntity(EntityManager em) {
        RelatedParty relatedParty = new RelatedParty()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return relatedParty;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(RelatedParty.class).block();
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
        relatedParty = createEntity(em);
    }

    @Test
    void createRelatedParty() throws Exception {
        int databaseSizeBeforeCreate = relatedPartyRepository.findAll().collectList().block().size();
        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeCreate + 1);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createRelatedPartyWithExistingId() throws Exception {
        // Create the RelatedParty with an existing ID
        relatedParty.setId("existing_id");
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        int databaseSizeBeforeCreate = relatedPartyRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRelatedParties() {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.save(relatedParty).block();

        // Get all the relatedPartyList
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
            .value(hasItem(relatedParty.getId()))
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
    void getRelatedParty() {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.save(relatedParty).block();

        // Get the relatedParty
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, relatedParty.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(relatedParty.getId()))
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
    void getNonExistingRelatedParty() {
        // Get the relatedParty
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRelatedParty() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.save(relatedParty).block();

        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();

        // Update the relatedParty
        RelatedParty updatedRelatedParty = relatedPartyRepository.findById(relatedParty.getId()).block();
        updatedRelatedParty
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(updatedRelatedParty);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, relatedPartyDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, relatedPartyDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRelatedPartyWithPatch() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.save(relatedParty).block();

        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();

        // Update the relatedParty using partial update
        RelatedParty partialUpdatedRelatedParty = new RelatedParty();
        partialUpdatedRelatedParty.setId(relatedParty.getId());

        partialUpdatedRelatedParty.href(UPDATED_HREF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRelatedParty.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedParty))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateRelatedPartyWithPatch() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.save(relatedParty).block();

        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();

        // Update the relatedParty using partial update
        RelatedParty partialUpdatedRelatedParty = new RelatedParty();
        partialUpdatedRelatedParty.setId(relatedParty.getId());

        partialUpdatedRelatedParty
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRelatedParty.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedParty))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, relatedPartyDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().collectList().block().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRelatedParty() {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.save(relatedParty).block();

        int databaseSizeBeforeDelete = relatedPartyRepository.findAll().collectList().block().size();

        // Delete the relatedParty
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, relatedParty.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll().collectList().block();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

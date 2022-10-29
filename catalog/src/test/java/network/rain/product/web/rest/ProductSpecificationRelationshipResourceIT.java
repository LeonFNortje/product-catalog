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
import network.rain.product.domain.ProductSpecificationRelationship;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.ProductSpecificationRelationshipRepository;
import network.rain.product.service.dto.ProductSpecificationRelationshipDTO;
import network.rain.product.service.mapper.ProductSpecificationRelationshipMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ProductSpecificationRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProductSpecificationRelationshipResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONSHIP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FOR_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_FOR_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-specification-relationships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductSpecificationRelationshipRepository productSpecificationRelationshipRepository;

    @Autowired
    private ProductSpecificationRelationshipMapper productSpecificationRelationshipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ProductSpecificationRelationship productSpecificationRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecificationRelationship createEntity(EntityManager em) {
        ProductSpecificationRelationship productSpecificationRelationship = new ProductSpecificationRelationship()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .relationshipType(DEFAULT_RELATIONSHIP_TYPE)
            .validForFrom(DEFAULT_VALID_FOR_FROM)
            .validForTo(DEFAULT_VALID_FOR_TO)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return productSpecificationRelationship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecificationRelationship createUpdatedEntity(EntityManager em) {
        ProductSpecificationRelationship productSpecificationRelationship = new ProductSpecificationRelationship()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return productSpecificationRelationship;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ProductSpecificationRelationship.class).block();
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
        productSpecificationRelationship = createEntity(em);
    }

    @Test
    void createProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationRelationshipRepository.findAll().collectList().block().size();
        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSpecificationRelationship testProductSpecificationRelationship = productSpecificationRelationshipList.get(
            productSpecificationRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationRelationship.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testProductSpecificationRelationship.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSpecificationRelationship.getRelationshipType()).isEqualTo(DEFAULT_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationRelationship.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testProductSpecificationRelationship.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecificationRelationship.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testProductSpecificationRelationship.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createProductSpecificationRelationshipWithExistingId() throws Exception {
        // Create the ProductSpecificationRelationship with an existing ID
        productSpecificationRelationship.setId("existing_id");
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        int databaseSizeBeforeCreate = productSpecificationRelationshipRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProductSpecificationRelationships() {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.save(productSpecificationRelationship).block();

        // Get all the productSpecificationRelationshipList
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
            .value(hasItem(productSpecificationRelationship.getId()))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].relationshipType")
            .value(hasItem(DEFAULT_RELATIONSHIP_TYPE))
            .jsonPath("$.[*].validForFrom")
            .value(hasItem(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.[*].validForTo")
            .value(hasItem(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getProductSpecificationRelationship() {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.save(productSpecificationRelationship).block();

        // Get the productSpecificationRelationship
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, productSpecificationRelationship.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(productSpecificationRelationship.getId()))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.relationshipType")
            .value(is(DEFAULT_RELATIONSHIP_TYPE))
            .jsonPath("$.validForFrom")
            .value(is(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.validForTo")
            .value(is(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingProductSpecificationRelationship() {
        // Get the productSpecificationRelationship
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProductSpecificationRelationship() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.save(productSpecificationRelationship).block();

        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();

        // Update the productSpecificationRelationship
        ProductSpecificationRelationship updatedProductSpecificationRelationship = productSpecificationRelationshipRepository
            .findById(productSpecificationRelationship.getId())
            .block();
        updatedProductSpecificationRelationship
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            updatedProductSpecificationRelationship
        );

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationRelationshipDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationRelationship testProductSpecificationRelationship = productSpecificationRelationshipList.get(
            productSpecificationRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationRelationship.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testProductSpecificationRelationship.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecificationRelationship.getRelationshipType()).isEqualTo(UPDATED_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationRelationship.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationRelationship.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecificationRelationship.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecificationRelationship.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationRelationshipDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductSpecificationRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.save(productSpecificationRelationship).block();

        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();

        // Update the productSpecificationRelationship using partial update
        ProductSpecificationRelationship partialUpdatedProductSpecificationRelationship = new ProductSpecificationRelationship();
        partialUpdatedProductSpecificationRelationship.setId(productSpecificationRelationship.getId());

        partialUpdatedProductSpecificationRelationship
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecificationRelationship.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationRelationship))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationRelationship testProductSpecificationRelationship = productSpecificationRelationshipList.get(
            productSpecificationRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationRelationship.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testProductSpecificationRelationship.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecificationRelationship.getRelationshipType()).isEqualTo(UPDATED_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationRelationship.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationRelationship.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecificationRelationship.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testProductSpecificationRelationship.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateProductSpecificationRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.save(productSpecificationRelationship).block();

        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();

        // Update the productSpecificationRelationship using partial update
        ProductSpecificationRelationship partialUpdatedProductSpecificationRelationship = new ProductSpecificationRelationship();
        partialUpdatedProductSpecificationRelationship.setId(productSpecificationRelationship.getId());

        partialUpdatedProductSpecificationRelationship
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecificationRelationship.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationRelationship))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationRelationship testProductSpecificationRelationship = productSpecificationRelationshipList.get(
            productSpecificationRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationRelationship.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testProductSpecificationRelationship.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecificationRelationship.getRelationshipType()).isEqualTo(UPDATED_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationRelationship.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationRelationship.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecificationRelationship.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecificationRelationship.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, productSpecificationRelationshipDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().collectList().block().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProductSpecificationRelationship() {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.save(productSpecificationRelationship).block();

        int databaseSizeBeforeDelete = productSpecificationRelationshipRepository.findAll().collectList().block().size();

        // Delete the productSpecificationRelationship
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, productSpecificationRelationship.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

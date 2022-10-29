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
import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.ProductSpecificationCharacteristicRelationshipRepository;
import network.rain.product.service.dto.ProductSpecificationCharacteristicRelationshipDTO;
import network.rain.product.service.mapper.ProductSpecificationCharacteristicRelationshipMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ProductSpecificationCharacteristicRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProductSpecificationCharacteristicRelationshipResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-specification-characteristic-relationships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductSpecificationCharacteristicRelationshipRepository productSpecificationCharacteristicRelationshipRepository;

    @Autowired
    private ProductSpecificationCharacteristicRelationshipMapper productSpecificationCharacteristicRelationshipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecificationCharacteristicRelationship createEntity(EntityManager em) {
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship = new ProductSpecificationCharacteristicRelationship()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .relationshipType(DEFAULT_RELATIONSHIP_TYPE)
            .validForFrom(DEFAULT_VALID_FOR_FROM)
            .validForTo(DEFAULT_VALID_FOR_TO)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return productSpecificationCharacteristicRelationship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecificationCharacteristicRelationship createUpdatedEntity(EntityManager em) {
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship = new ProductSpecificationCharacteristicRelationship()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return productSpecificationCharacteristicRelationship;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ProductSpecificationCharacteristicRelationship.class).block();
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
        productSpecificationCharacteristicRelationship = createEntity(em);
    }

    @Test
    void createProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();
        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSpecificationCharacteristicRelationship testProductSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationshipList.get(
            productSpecificationCharacteristicRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristicRelationship.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testProductSpecificationCharacteristicRelationship.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSpecificationCharacteristicRelationship.getRelationshipType()).isEqualTo(DEFAULT_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristicRelationship.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristicRelationship.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createProductSpecificationCharacteristicRelationshipWithExistingId() throws Exception {
        // Create the ProductSpecificationCharacteristicRelationship with an existing ID
        productSpecificationCharacteristicRelationship.setId("existing_id");
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        int databaseSizeBeforeCreate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProductSpecificationCharacteristicRelationships() {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.save(productSpecificationCharacteristicRelationship).block();

        // Get all the productSpecificationCharacteristicRelationshipList
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
            .value(hasItem(productSpecificationCharacteristicRelationship.getId()))
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
    void getProductSpecificationCharacteristicRelationship() {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.save(productSpecificationCharacteristicRelationship).block();

        // Get the productSpecificationCharacteristicRelationship
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationship.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(productSpecificationCharacteristicRelationship.getId()))
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
    void getNonExistingProductSpecificationCharacteristicRelationship() {
        // Get the productSpecificationCharacteristicRelationship
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProductSpecificationCharacteristicRelationship() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.save(productSpecificationCharacteristicRelationship).block();

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();

        // Update the productSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationship updatedProductSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationshipRepository
            .findById(productSpecificationCharacteristicRelationship.getId())
            .block();
        updatedProductSpecificationCharacteristicRelationship
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            updatedProductSpecificationCharacteristicRelationship
        );

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationshipDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationCharacteristicRelationship testProductSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationshipList.get(
            productSpecificationCharacteristicRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristicRelationship.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testProductSpecificationCharacteristicRelationship.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecificationCharacteristicRelationship.getRelationshipType()).isEqualTo(UPDATED_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristicRelationship.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristicRelationship.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationshipDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductSpecificationCharacteristicRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.save(productSpecificationCharacteristicRelationship).block();

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();

        // Update the productSpecificationCharacteristicRelationship using partial update
        ProductSpecificationCharacteristicRelationship partialUpdatedProductSpecificationCharacteristicRelationship = new ProductSpecificationCharacteristicRelationship();
        partialUpdatedProductSpecificationCharacteristicRelationship.setId(productSpecificationCharacteristicRelationship.getId());

        partialUpdatedProductSpecificationCharacteristicRelationship.schemaLocation(UPDATED_SCHEMA_LOCATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristicRelationship.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristicRelationship))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationCharacteristicRelationship testProductSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationshipList.get(
            productSpecificationCharacteristicRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristicRelationship.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testProductSpecificationCharacteristicRelationship.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSpecificationCharacteristicRelationship.getRelationshipType()).isEqualTo(DEFAULT_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristicRelationship.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristicRelationship.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateProductSpecificationCharacteristicRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.save(productSpecificationCharacteristicRelationship).block();

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();

        // Update the productSpecificationCharacteristicRelationship using partial update
        ProductSpecificationCharacteristicRelationship partialUpdatedProductSpecificationCharacteristicRelationship = new ProductSpecificationCharacteristicRelationship();
        partialUpdatedProductSpecificationCharacteristicRelationship.setId(productSpecificationCharacteristicRelationship.getId());

        partialUpdatedProductSpecificationCharacteristicRelationship
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristicRelationship.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristicRelationship))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationCharacteristicRelationship testProductSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationshipList.get(
            productSpecificationCharacteristicRelationshipList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristicRelationship.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testProductSpecificationCharacteristicRelationship.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecificationCharacteristicRelationship.getRelationshipType()).isEqualTo(UPDATED_RELATIONSHIP_TYPE);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristicRelationship.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristicRelationship.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristicRelationship.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationshipDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProductSpecificationCharacteristicRelationship() {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.save(productSpecificationCharacteristicRelationship).block();

        int databaseSizeBeforeDelete = productSpecificationCharacteristicRelationshipRepository.findAll().collectList().block().size();

        // Delete the productSpecificationCharacteristicRelationship
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationship.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

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
import network.rain.product.domain.ProductSpecification;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.ProductSpecificationRepository;
import network.rain.product.service.dto.ProductSpecificationDTO;
import network.rain.product.service.mapper.ProductSpecificationMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ProductSpecificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProductSpecificationResourceIT {

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_BUNDLE = false;
    private static final Boolean UPDATED_IS_BUNDLE = true;

    private static final Instant DEFAULT_LAST_UPDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIFECYCLE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_LIFECYCLE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FOR_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_FOR_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-specifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    @Autowired
    private ProductSpecificationMapper productSpecificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ProductSpecification productSpecification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecification createEntity(EntityManager em) {
        ProductSpecification productSpecification = new ProductSpecification()
            .brand(DEFAULT_BRAND)
            .description(DEFAULT_DESCRIPTION)
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .isBundle(DEFAULT_IS_BUNDLE)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .lifecycleStatus(DEFAULT_LIFECYCLE_STATUS)
            .productNumber(DEFAULT_PRODUCT_NUMBER)
            .validForFrom(DEFAULT_VALID_FOR_FROM)
            .validForTo(DEFAULT_VALID_FOR_TO)
            .version(DEFAULT_VERSION)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return productSpecification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecification createUpdatedEntity(EntityManager em) {
        ProductSpecification productSpecification = new ProductSpecification()
            .brand(UPDATED_BRAND)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .isBundle(UPDATED_IS_BUNDLE)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return productSpecification;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ProductSpecification.class).block();
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
        productSpecification = createEntity(em);
    }

    @Test
    void createProductSpecification() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationRepository.findAll().collectList().block().size();
        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSpecification testProductSpecification = productSpecificationList.get(productSpecificationList.size() - 1);
        assertThat(testProductSpecification.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProductSpecification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductSpecification.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testProductSpecification.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSpecification.getIsBundle()).isEqualTo(DEFAULT_IS_BUNDLE);
        assertThat(testProductSpecification.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testProductSpecification.getLifecycleStatus()).isEqualTo(DEFAULT_LIFECYCLE_STATUS);
        assertThat(testProductSpecification.getProductNumber()).isEqualTo(DEFAULT_PRODUCT_NUMBER);
        assertThat(testProductSpecification.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testProductSpecification.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecification.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProductSpecification.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testProductSpecification.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createProductSpecificationWithExistingId() throws Exception {
        // Create the ProductSpecification with an existing ID
        productSpecification.setId("existing_id");
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        int databaseSizeBeforeCreate = productSpecificationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProductSpecifications() {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.save(productSpecification).block();

        // Get all the productSpecificationList
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
            .value(hasItem(productSpecification.getId()))
            .jsonPath("$.[*].brand")
            .value(hasItem(DEFAULT_BRAND))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].isBundle")
            .value(hasItem(DEFAULT_IS_BUNDLE.booleanValue()))
            .jsonPath("$.[*].lastUpdate")
            .value(hasItem(DEFAULT_LAST_UPDATE.toString()))
            .jsonPath("$.[*].lifecycleStatus")
            .value(hasItem(DEFAULT_LIFECYCLE_STATUS))
            .jsonPath("$.[*].productNumber")
            .value(hasItem(DEFAULT_PRODUCT_NUMBER))
            .jsonPath("$.[*].validForFrom")
            .value(hasItem(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.[*].validForTo")
            .value(hasItem(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.[*].version")
            .value(hasItem(DEFAULT_VERSION))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getProductSpecification() {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.save(productSpecification).block();

        // Get the productSpecification
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, productSpecification.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(productSpecification.getId()))
            .jsonPath("$.brand")
            .value(is(DEFAULT_BRAND))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.isBundle")
            .value(is(DEFAULT_IS_BUNDLE.booleanValue()))
            .jsonPath("$.lastUpdate")
            .value(is(DEFAULT_LAST_UPDATE.toString()))
            .jsonPath("$.lifecycleStatus")
            .value(is(DEFAULT_LIFECYCLE_STATUS))
            .jsonPath("$.productNumber")
            .value(is(DEFAULT_PRODUCT_NUMBER))
            .jsonPath("$.validForFrom")
            .value(is(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.validForTo")
            .value(is(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.version")
            .value(is(DEFAULT_VERSION))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingProductSpecification() {
        // Get the productSpecification
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProductSpecification() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.save(productSpecification).block();

        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();

        // Update the productSpecification
        ProductSpecification updatedProductSpecification = productSpecificationRepository.findById(productSpecification.getId()).block();
        updatedProductSpecification
            .brand(UPDATED_BRAND)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .isBundle(UPDATED_IS_BUNDLE)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(updatedProductSpecification);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecification testProductSpecification = productSpecificationList.get(productSpecificationList.size() - 1);
        assertThat(testProductSpecification.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProductSpecification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductSpecification.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testProductSpecification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecification.getIsBundle()).isEqualTo(UPDATED_IS_BUNDLE);
        assertThat(testProductSpecification.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testProductSpecification.getLifecycleStatus()).isEqualTo(UPDATED_LIFECYCLE_STATUS);
        assertThat(testProductSpecification.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testProductSpecification.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecification.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecification.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProductSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.save(productSpecification).block();

        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();

        // Update the productSpecification using partial update
        ProductSpecification partialUpdatedProductSpecification = new ProductSpecification();
        partialUpdatedProductSpecification.setId(productSpecification.getId());

        partialUpdatedProductSpecification
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecification.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecification))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecification testProductSpecification = productSpecificationList.get(productSpecificationList.size() - 1);
        assertThat(testProductSpecification.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProductSpecification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductSpecification.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testProductSpecification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecification.getIsBundle()).isEqualTo(DEFAULT_IS_BUNDLE);
        assertThat(testProductSpecification.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testProductSpecification.getLifecycleStatus()).isEqualTo(DEFAULT_LIFECYCLE_STATUS);
        assertThat(testProductSpecification.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testProductSpecification.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testProductSpecification.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecification.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProductSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void fullUpdateProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.save(productSpecification).block();

        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();

        // Update the productSpecification using partial update
        ProductSpecification partialUpdatedProductSpecification = new ProductSpecification();
        partialUpdatedProductSpecification.setId(productSpecification.getId());

        partialUpdatedProductSpecification
            .brand(UPDATED_BRAND)
            .description(UPDATED_DESCRIPTION)
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .isBundle(UPDATED_IS_BUNDLE)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .lifecycleStatus(UPDATED_LIFECYCLE_STATUS)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecification.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecification))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecification testProductSpecification = productSpecificationList.get(productSpecificationList.size() - 1);
        assertThat(testProductSpecification.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProductSpecification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductSpecification.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testProductSpecification.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecification.getIsBundle()).isEqualTo(UPDATED_IS_BUNDLE);
        assertThat(testProductSpecification.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testProductSpecification.getLifecycleStatus()).isEqualTo(UPDATED_LIFECYCLE_STATUS);
        assertThat(testProductSpecification.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testProductSpecification.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecification.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecification.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProductSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, productSpecificationDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().collectList().block().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProductSpecification() {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.save(productSpecification).block();

        int databaseSizeBeforeDelete = productSpecificationRepository.findAll().collectList().block().size();

        // Delete the productSpecification
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, productSpecification.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll().collectList().block();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

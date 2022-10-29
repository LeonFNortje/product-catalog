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
import network.rain.product.domain.ProductSpecificationCharacteristic;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.ProductSpecificationCharacteristicRepository;
import network.rain.product.service.dto.ProductSpecificationCharacteristicDTO;
import network.rain.product.service.mapper.ProductSpecificationCharacteristicMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ProductSpecificationCharacteristicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProductSpecificationCharacteristicResourceIT {

    private static final Boolean DEFAULT_CONFIGURABLE = false;
    private static final Boolean UPDATED_CONFIGURABLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXTENSIBLE = false;
    private static final Boolean UPDATED_EXTENSIBLE = true;

    private static final Boolean DEFAULT_IS_UNIQUE = false;
    private static final Boolean UPDATED_IS_UNIQUE = true;

    private static final Integer DEFAULT_MAX_CARDINALITY = 1;
    private static final Integer UPDATED_MAX_CARDINALITY = 2;

    private static final Integer DEFAULT_MIN_CARDINALITY = 1;
    private static final Integer UPDATED_MIN_CARDINALITY = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_REGEX = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FOR_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_FOR_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-specification-characteristics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductSpecificationCharacteristicRepository productSpecificationCharacteristicRepository;

    @Autowired
    private ProductSpecificationCharacteristicMapper productSpecificationCharacteristicMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ProductSpecificationCharacteristic productSpecificationCharacteristic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecificationCharacteristic createEntity(EntityManager em) {
        ProductSpecificationCharacteristic productSpecificationCharacteristic = new ProductSpecificationCharacteristic()
            .configurable(DEFAULT_CONFIGURABLE)
            .description(DEFAULT_DESCRIPTION)
            .extensible(DEFAULT_EXTENSIBLE)
            .isUnique(DEFAULT_IS_UNIQUE)
            .maxCardinality(DEFAULT_MAX_CARDINALITY)
            .minCardinality(DEFAULT_MIN_CARDINALITY)
            .name(DEFAULT_NAME)
            .regex(DEFAULT_REGEX)
            .validForFrom(DEFAULT_VALID_FOR_FROM)
            .validForTo(DEFAULT_VALID_FOR_TO)
            .valueType(DEFAULT_VALUE_TYPE)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE)
            .valueSchemaLocation(DEFAULT_VALUE_SCHEMA_LOCATION);
        return productSpecificationCharacteristic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSpecificationCharacteristic createUpdatedEntity(EntityManager em) {
        ProductSpecificationCharacteristic productSpecificationCharacteristic = new ProductSpecificationCharacteristic()
            .configurable(UPDATED_CONFIGURABLE)
            .description(UPDATED_DESCRIPTION)
            .extensible(UPDATED_EXTENSIBLE)
            .isUnique(UPDATED_IS_UNIQUE)
            .maxCardinality(UPDATED_MAX_CARDINALITY)
            .minCardinality(UPDATED_MIN_CARDINALITY)
            .name(UPDATED_NAME)
            .regex(UPDATED_REGEX)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE)
            .valueSchemaLocation(UPDATED_VALUE_SCHEMA_LOCATION);
        return productSpecificationCharacteristic;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ProductSpecificationCharacteristic.class).block();
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
        productSpecificationCharacteristic = createEntity(em);
    }

    @Test
    void createProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();
        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSpecificationCharacteristic testProductSpecificationCharacteristic = productSpecificationCharacteristicList.get(
            productSpecificationCharacteristicList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristic.getConfigurable()).isEqualTo(DEFAULT_CONFIGURABLE);
        assertThat(testProductSpecificationCharacteristic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductSpecificationCharacteristic.getExtensible()).isEqualTo(DEFAULT_EXTENSIBLE);
        assertThat(testProductSpecificationCharacteristic.getIsUnique()).isEqualTo(DEFAULT_IS_UNIQUE);
        assertThat(testProductSpecificationCharacteristic.getMaxCardinality()).isEqualTo(DEFAULT_MAX_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getMinCardinality()).isEqualTo(DEFAULT_MIN_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSpecificationCharacteristic.getRegex()).isEqualTo(DEFAULT_REGEX);
        assertThat(testProductSpecificationCharacteristic.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristic.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristic.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testProductSpecificationCharacteristic.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristic.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductSpecificationCharacteristic.getValueSchemaLocation()).isEqualTo(DEFAULT_VALUE_SCHEMA_LOCATION);
    }

    @Test
    void createProductSpecificationCharacteristicWithExistingId() throws Exception {
        // Create the ProductSpecificationCharacteristic with an existing ID
        productSpecificationCharacteristic.setId("existing_id");
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        int databaseSizeBeforeCreate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProductSpecificationCharacteristics() {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.save(productSpecificationCharacteristic).block();

        // Get all the productSpecificationCharacteristicList
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
            .value(hasItem(productSpecificationCharacteristic.getId()))
            .jsonPath("$.[*].configurable")
            .value(hasItem(DEFAULT_CONFIGURABLE.booleanValue()))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].extensible")
            .value(hasItem(DEFAULT_EXTENSIBLE.booleanValue()))
            .jsonPath("$.[*].isUnique")
            .value(hasItem(DEFAULT_IS_UNIQUE.booleanValue()))
            .jsonPath("$.[*].maxCardinality")
            .value(hasItem(DEFAULT_MAX_CARDINALITY))
            .jsonPath("$.[*].minCardinality")
            .value(hasItem(DEFAULT_MIN_CARDINALITY))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].regex")
            .value(hasItem(DEFAULT_REGEX))
            .jsonPath("$.[*].validForFrom")
            .value(hasItem(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.[*].validForTo")
            .value(hasItem(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.[*].valueType")
            .value(hasItem(DEFAULT_VALUE_TYPE))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE))
            .jsonPath("$.[*].valueSchemaLocation")
            .value(hasItem(DEFAULT_VALUE_SCHEMA_LOCATION));
    }

    @Test
    void getProductSpecificationCharacteristic() {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.save(productSpecificationCharacteristic).block();

        // Get the productSpecificationCharacteristic
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristic.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(productSpecificationCharacteristic.getId()))
            .jsonPath("$.configurable")
            .value(is(DEFAULT_CONFIGURABLE.booleanValue()))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.extensible")
            .value(is(DEFAULT_EXTENSIBLE.booleanValue()))
            .jsonPath("$.isUnique")
            .value(is(DEFAULT_IS_UNIQUE.booleanValue()))
            .jsonPath("$.maxCardinality")
            .value(is(DEFAULT_MAX_CARDINALITY))
            .jsonPath("$.minCardinality")
            .value(is(DEFAULT_MIN_CARDINALITY))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.regex")
            .value(is(DEFAULT_REGEX))
            .jsonPath("$.validForFrom")
            .value(is(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.validForTo")
            .value(is(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.valueType")
            .value(is(DEFAULT_VALUE_TYPE))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE))
            .jsonPath("$.valueSchemaLocation")
            .value(is(DEFAULT_VALUE_SCHEMA_LOCATION));
    }

    @Test
    void getNonExistingProductSpecificationCharacteristic() {
        // Get the productSpecificationCharacteristic
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProductSpecificationCharacteristic() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.save(productSpecificationCharacteristic).block();

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();

        // Update the productSpecificationCharacteristic
        ProductSpecificationCharacteristic updatedProductSpecificationCharacteristic = productSpecificationCharacteristicRepository
            .findById(productSpecificationCharacteristic.getId())
            .block();
        updatedProductSpecificationCharacteristic
            .configurable(UPDATED_CONFIGURABLE)
            .description(UPDATED_DESCRIPTION)
            .extensible(UPDATED_EXTENSIBLE)
            .isUnique(UPDATED_IS_UNIQUE)
            .maxCardinality(UPDATED_MAX_CARDINALITY)
            .minCardinality(UPDATED_MIN_CARDINALITY)
            .name(UPDATED_NAME)
            .regex(UPDATED_REGEX)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE)
            .valueSchemaLocation(UPDATED_VALUE_SCHEMA_LOCATION);
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            updatedProductSpecificationCharacteristic
        );

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationCharacteristic testProductSpecificationCharacteristic = productSpecificationCharacteristicList.get(
            productSpecificationCharacteristicList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristic.getConfigurable()).isEqualTo(UPDATED_CONFIGURABLE);
        assertThat(testProductSpecificationCharacteristic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductSpecificationCharacteristic.getExtensible()).isEqualTo(UPDATED_EXTENSIBLE);
        assertThat(testProductSpecificationCharacteristic.getIsUnique()).isEqualTo(UPDATED_IS_UNIQUE);
        assertThat(testProductSpecificationCharacteristic.getMaxCardinality()).isEqualTo(UPDATED_MAX_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getMinCardinality()).isEqualTo(UPDATED_MIN_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecificationCharacteristic.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testProductSpecificationCharacteristic.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristic.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristic.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testProductSpecificationCharacteristic.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristic.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductSpecificationCharacteristic.getValueSchemaLocation()).isEqualTo(UPDATED_VALUE_SCHEMA_LOCATION);
    }

    @Test
    void putNonExistingProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProductSpecificationCharacteristicWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.save(productSpecificationCharacteristic).block();

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();

        // Update the productSpecificationCharacteristic using partial update
        ProductSpecificationCharacteristic partialUpdatedProductSpecificationCharacteristic = new ProductSpecificationCharacteristic();
        partialUpdatedProductSpecificationCharacteristic.setId(productSpecificationCharacteristic.getId());

        partialUpdatedProductSpecificationCharacteristic.configurable(UPDATED_CONFIGURABLE).validForFrom(UPDATED_VALID_FOR_FROM);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristic.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristic))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationCharacteristic testProductSpecificationCharacteristic = productSpecificationCharacteristicList.get(
            productSpecificationCharacteristicList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristic.getConfigurable()).isEqualTo(UPDATED_CONFIGURABLE);
        assertThat(testProductSpecificationCharacteristic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductSpecificationCharacteristic.getExtensible()).isEqualTo(DEFAULT_EXTENSIBLE);
        assertThat(testProductSpecificationCharacteristic.getIsUnique()).isEqualTo(DEFAULT_IS_UNIQUE);
        assertThat(testProductSpecificationCharacteristic.getMaxCardinality()).isEqualTo(DEFAULT_MAX_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getMinCardinality()).isEqualTo(DEFAULT_MIN_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSpecificationCharacteristic.getRegex()).isEqualTo(DEFAULT_REGEX);
        assertThat(testProductSpecificationCharacteristic.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristic.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristic.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testProductSpecificationCharacteristic.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristic.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductSpecificationCharacteristic.getValueSchemaLocation()).isEqualTo(DEFAULT_VALUE_SCHEMA_LOCATION);
    }

    @Test
    void fullUpdateProductSpecificationCharacteristicWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.save(productSpecificationCharacteristic).block();

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();

        // Update the productSpecificationCharacteristic using partial update
        ProductSpecificationCharacteristic partialUpdatedProductSpecificationCharacteristic = new ProductSpecificationCharacteristic();
        partialUpdatedProductSpecificationCharacteristic.setId(productSpecificationCharacteristic.getId());

        partialUpdatedProductSpecificationCharacteristic
            .configurable(UPDATED_CONFIGURABLE)
            .description(UPDATED_DESCRIPTION)
            .extensible(UPDATED_EXTENSIBLE)
            .isUnique(UPDATED_IS_UNIQUE)
            .maxCardinality(UPDATED_MAX_CARDINALITY)
            .minCardinality(UPDATED_MIN_CARDINALITY)
            .name(UPDATED_NAME)
            .regex(UPDATED_REGEX)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE)
            .valueSchemaLocation(UPDATED_VALUE_SCHEMA_LOCATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristic.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristic))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        ProductSpecificationCharacteristic testProductSpecificationCharacteristic = productSpecificationCharacteristicList.get(
            productSpecificationCharacteristicList.size() - 1
        );
        assertThat(testProductSpecificationCharacteristic.getConfigurable()).isEqualTo(UPDATED_CONFIGURABLE);
        assertThat(testProductSpecificationCharacteristic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductSpecificationCharacteristic.getExtensible()).isEqualTo(UPDATED_EXTENSIBLE);
        assertThat(testProductSpecificationCharacteristic.getIsUnique()).isEqualTo(UPDATED_IS_UNIQUE);
        assertThat(testProductSpecificationCharacteristic.getMaxCardinality()).isEqualTo(UPDATED_MAX_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getMinCardinality()).isEqualTo(UPDATED_MIN_CARDINALITY);
        assertThat(testProductSpecificationCharacteristic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSpecificationCharacteristic.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testProductSpecificationCharacteristic.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testProductSpecificationCharacteristic.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testProductSpecificationCharacteristic.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testProductSpecificationCharacteristic.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testProductSpecificationCharacteristic.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductSpecificationCharacteristic.getValueSchemaLocation()).isEqualTo(UPDATED_VALUE_SCHEMA_LOCATION);
    }

    @Test
    void patchNonExistingProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristicDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().collectList().block().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProductSpecificationCharacteristic() {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.save(productSpecificationCharacteristic).block();

        int databaseSizeBeforeDelete = productSpecificationCharacteristicRepository.findAll().collectList().block().size();

        // Delete the productSpecificationCharacteristic
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, productSpecificationCharacteristic.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository
            .findAll()
            .collectList()
            .block();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.ProductSpecificationCharacteristic;
import network.rain.product.repository.ProductSpecificationCharacteristicRepository;
import network.rain.product.service.dto.ProductSpecificationCharacteristicDTO;
import network.rain.product.service.mapper.ProductSpecificationCharacteristicMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductSpecificationCharacteristicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restProductSpecificationCharacteristicMockMvc;

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

    @BeforeEach
    public void initTest() {
        productSpecificationCharacteristic = createEntity(em);
    }

    @Test
    @Transactional
    void createProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationCharacteristicRepository.findAll().size();
        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );
        restProductSpecificationCharacteristicMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
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
    @Transactional
    void createProductSpecificationCharacteristicWithExistingId() throws Exception {
        // Create the ProductSpecificationCharacteristic with an existing ID
        productSpecificationCharacteristic.setId("existing_id");
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        int databaseSizeBeforeCreate = productSpecificationCharacteristicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSpecificationCharacteristicMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductSpecificationCharacteristics() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.saveAndFlush(productSpecificationCharacteristic);

        // Get all the productSpecificationCharacteristicList
        restProductSpecificationCharacteristicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSpecificationCharacteristic.getId())))
            .andExpect(jsonPath("$.[*].configurable").value(hasItem(DEFAULT_CONFIGURABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].extensible").value(hasItem(DEFAULT_EXTENSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].isUnique").value(hasItem(DEFAULT_IS_UNIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].maxCardinality").value(hasItem(DEFAULT_MAX_CARDINALITY)))
            .andExpect(jsonPath("$.[*].minCardinality").value(hasItem(DEFAULT_MIN_CARDINALITY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].regex").value(hasItem(DEFAULT_REGEX)))
            .andExpect(jsonPath("$.[*].validForFrom").value(hasItem(DEFAULT_VALID_FOR_FROM.toString())))
            .andExpect(jsonPath("$.[*].validForTo").value(hasItem(DEFAULT_VALID_FOR_TO.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].valueSchemaLocation").value(hasItem(DEFAULT_VALUE_SCHEMA_LOCATION)));
    }

    @Test
    @Transactional
    void getProductSpecificationCharacteristic() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.saveAndFlush(productSpecificationCharacteristic);

        // Get the productSpecificationCharacteristic
        restProductSpecificationCharacteristicMockMvc
            .perform(get(ENTITY_API_URL_ID, productSpecificationCharacteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSpecificationCharacteristic.getId()))
            .andExpect(jsonPath("$.configurable").value(DEFAULT_CONFIGURABLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.extensible").value(DEFAULT_EXTENSIBLE.booleanValue()))
            .andExpect(jsonPath("$.isUnique").value(DEFAULT_IS_UNIQUE.booleanValue()))
            .andExpect(jsonPath("$.maxCardinality").value(DEFAULT_MAX_CARDINALITY))
            .andExpect(jsonPath("$.minCardinality").value(DEFAULT_MIN_CARDINALITY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.regex").value(DEFAULT_REGEX))
            .andExpect(jsonPath("$.validForFrom").value(DEFAULT_VALID_FOR_FROM.toString()))
            .andExpect(jsonPath("$.validForTo").value(DEFAULT_VALID_FOR_TO.toString()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.valueSchemaLocation").value(DEFAULT_VALUE_SCHEMA_LOCATION));
    }

    @Test
    @Transactional
    void getNonExistingProductSpecificationCharacteristic() throws Exception {
        // Get the productSpecificationCharacteristic
        restProductSpecificationCharacteristicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductSpecificationCharacteristic() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.saveAndFlush(productSpecificationCharacteristic);

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();

        // Update the productSpecificationCharacteristic
        ProductSpecificationCharacteristic updatedProductSpecificationCharacteristic = productSpecificationCharacteristicRepository
            .findById(productSpecificationCharacteristic.getId())
            .get();
        // Disconnect from session so that the updates on updatedProductSpecificationCharacteristic are not directly saved in db
        em.detach(updatedProductSpecificationCharacteristic);
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

        restProductSpecificationCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationCharacteristicDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
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
    @Transactional
    void putNonExistingProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationCharacteristicDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductSpecificationCharacteristicWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.saveAndFlush(productSpecificationCharacteristic);

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();

        // Update the productSpecificationCharacteristic using partial update
        ProductSpecificationCharacteristic partialUpdatedProductSpecificationCharacteristic = new ProductSpecificationCharacteristic();
        partialUpdatedProductSpecificationCharacteristic.setId(productSpecificationCharacteristic.getId());

        partialUpdatedProductSpecificationCharacteristic.configurable(UPDATED_CONFIGURABLE).validForFrom(UPDATED_VALID_FOR_FROM);

        restProductSpecificationCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristic))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
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
    @Transactional
    void fullUpdateProductSpecificationCharacteristicWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.saveAndFlush(productSpecificationCharacteristic);

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();

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

        restProductSpecificationCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristic))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
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
    @Transactional
    void patchNonExistingProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productSpecificationCharacteristicDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductSpecificationCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRepository.findAll().size();
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristic
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO = productSpecificationCharacteristicMapper.toDto(
            productSpecificationCharacteristic
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecificationCharacteristic in the database
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductSpecificationCharacteristic() throws Exception {
        // Initialize the database
        productSpecificationCharacteristic.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRepository.saveAndFlush(productSpecificationCharacteristic);

        int databaseSizeBeforeDelete = productSpecificationCharacteristicRepository.findAll().size();

        // Delete the productSpecificationCharacteristic
        restProductSpecificationCharacteristicMockMvc
            .perform(delete(ENTITY_API_URL_ID, productSpecificationCharacteristic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSpecificationCharacteristic> productSpecificationCharacteristicList = productSpecificationCharacteristicRepository.findAll();
        assertThat(productSpecificationCharacteristicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

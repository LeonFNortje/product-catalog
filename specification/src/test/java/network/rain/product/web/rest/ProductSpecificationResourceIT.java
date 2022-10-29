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
import network.rain.product.domain.ProductSpecification;
import network.rain.product.repository.ProductSpecificationRepository;
import network.rain.product.service.dto.ProductSpecificationDTO;
import network.rain.product.service.mapper.ProductSpecificationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductSpecificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restProductSpecificationMockMvc;

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

    @BeforeEach
    public void initTest() {
        productSpecification = createEntity(em);
    }

    @Test
    @Transactional
    void createProductSpecification() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationRepository.findAll().size();
        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);
        restProductSpecificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
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
    @Transactional
    void createProductSpecificationWithExistingId() throws Exception {
        // Create the ProductSpecification with an existing ID
        productSpecification.setId("existing_id");
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        int databaseSizeBeforeCreate = productSpecificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSpecificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductSpecifications() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.saveAndFlush(productSpecification);

        // Get all the productSpecificationList
        restProductSpecificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSpecification.getId())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isBundle").value(hasItem(DEFAULT_IS_BUNDLE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].lifecycleStatus").value(hasItem(DEFAULT_LIFECYCLE_STATUS)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].validForFrom").value(hasItem(DEFAULT_VALID_FOR_FROM.toString())))
            .andExpect(jsonPath("$.[*].validForTo").value(hasItem(DEFAULT_VALID_FOR_TO.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getProductSpecification() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.saveAndFlush(productSpecification);

        // Get the productSpecification
        restProductSpecificationMockMvc
            .perform(get(ENTITY_API_URL_ID, productSpecification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSpecification.getId()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isBundle").value(DEFAULT_IS_BUNDLE.booleanValue()))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.lifecycleStatus").value(DEFAULT_LIFECYCLE_STATUS))
            .andExpect(jsonPath("$.productNumber").value(DEFAULT_PRODUCT_NUMBER))
            .andExpect(jsonPath("$.validForFrom").value(DEFAULT_VALID_FOR_FROM.toString()))
            .andExpect(jsonPath("$.validForTo").value(DEFAULT_VALID_FOR_TO.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingProductSpecification() throws Exception {
        // Get the productSpecification
        restProductSpecificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductSpecification() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.saveAndFlush(productSpecification);

        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();

        // Update the productSpecification
        ProductSpecification updatedProductSpecification = productSpecificationRepository.findById(productSpecification.getId()).get();
        // Disconnect from session so that the updates on updatedProductSpecification are not directly saved in db
        em.detach(updatedProductSpecification);
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

        restProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
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
    @Transactional
    void putNonExistingProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.saveAndFlush(productSpecification);

        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();

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

        restProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecification))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
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
    @Transactional
    void fullUpdateProductSpecificationWithPatch() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.saveAndFlush(productSpecification);

        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();

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

        restProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecification))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
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
    @Transactional
    void patchNonExistingProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productSpecificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductSpecification() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRepository.findAll().size();
        productSpecification.setId(UUID.randomUUID().toString());

        // Create the ProductSpecification
        ProductSpecificationDTO productSpecificationDTO = productSpecificationMapper.toDto(productSpecification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecification in the database
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductSpecification() throws Exception {
        // Initialize the database
        productSpecification.setId(UUID.randomUUID().toString());
        productSpecificationRepository.saveAndFlush(productSpecification);

        int databaseSizeBeforeDelete = productSpecificationRepository.findAll().size();

        // Delete the productSpecification
        restProductSpecificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, productSpecification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSpecification> productSpecificationList = productSpecificationRepository.findAll();
        assertThat(productSpecificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

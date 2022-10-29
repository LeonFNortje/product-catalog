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
import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import network.rain.product.repository.ProductSpecificationCharacteristicRelationshipRepository;
import network.rain.product.service.dto.ProductSpecificationCharacteristicRelationshipDTO;
import network.rain.product.service.mapper.ProductSpecificationCharacteristicRelationshipMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductSpecificationCharacteristicRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restProductSpecificationCharacteristicRelationshipMockMvc;

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

    @BeforeEach
    public void initTest() {
        productSpecificationCharacteristicRelationship = createEntity(em);
    }

    @Test
    @Transactional
    void createProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationCharacteristicRelationshipRepository.findAll().size();
        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
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
    @Transactional
    void createProductSpecificationCharacteristicRelationshipWithExistingId() throws Exception {
        // Create the ProductSpecificationCharacteristicRelationship with an existing ID
        productSpecificationCharacteristicRelationship.setId("existing_id");
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        int databaseSizeBeforeCreate = productSpecificationCharacteristicRelationshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductSpecificationCharacteristicRelationships() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.saveAndFlush(productSpecificationCharacteristicRelationship);

        // Get all the productSpecificationCharacteristicRelationshipList
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSpecificationCharacteristicRelationship.getId())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].relationshipType").value(hasItem(DEFAULT_RELATIONSHIP_TYPE)))
            .andExpect(jsonPath("$.[*].validForFrom").value(hasItem(DEFAULT_VALID_FOR_FROM.toString())))
            .andExpect(jsonPath("$.[*].validForTo").value(hasItem(DEFAULT_VALID_FOR_TO.toString())))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getProductSpecificationCharacteristicRelationship() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.saveAndFlush(productSpecificationCharacteristicRelationship);

        // Get the productSpecificationCharacteristicRelationship
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(get(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSpecificationCharacteristicRelationship.getId()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.relationshipType").value(DEFAULT_RELATIONSHIP_TYPE))
            .andExpect(jsonPath("$.validForFrom").value(DEFAULT_VALID_FOR_FROM.toString()))
            .andExpect(jsonPath("$.validForTo").value(DEFAULT_VALID_FOR_TO.toString()))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingProductSpecificationCharacteristicRelationship() throws Exception {
        // Get the productSpecificationCharacteristicRelationship
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductSpecificationCharacteristicRelationship() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.saveAndFlush(productSpecificationCharacteristicRelationship);

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();

        // Update the productSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationship updatedProductSpecificationCharacteristicRelationship = productSpecificationCharacteristicRelationshipRepository
            .findById(productSpecificationCharacteristicRelationship.getId())
            .get();
        // Disconnect from session so that the updates on updatedProductSpecificationCharacteristicRelationship are not directly saved in db
        em.detach(updatedProductSpecificationCharacteristicRelationship);
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

        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
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
    @Transactional
    void putNonExistingProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductSpecificationCharacteristicRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.saveAndFlush(productSpecificationCharacteristicRelationship);

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();

        // Update the productSpecificationCharacteristicRelationship using partial update
        ProductSpecificationCharacteristicRelationship partialUpdatedProductSpecificationCharacteristicRelationship = new ProductSpecificationCharacteristicRelationship();
        partialUpdatedProductSpecificationCharacteristicRelationship.setId(productSpecificationCharacteristicRelationship.getId());

        partialUpdatedProductSpecificationCharacteristicRelationship.schemaLocation(UPDATED_SCHEMA_LOCATION);

        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristicRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristicRelationship))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
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
    @Transactional
    void fullUpdateProductSpecificationCharacteristicRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.saveAndFlush(productSpecificationCharacteristicRelationship);

        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();

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

        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecificationCharacteristicRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationCharacteristicRelationship))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
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
    @Transactional
    void patchNonExistingProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationshipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductSpecificationCharacteristicRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationCharacteristicRelationshipRepository.findAll().size();
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationCharacteristicRelationship
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO = productSpecificationCharacteristicRelationshipMapper.toDto(
            productSpecificationCharacteristicRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationCharacteristicRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecificationCharacteristicRelationship in the database
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductSpecificationCharacteristicRelationship() throws Exception {
        // Initialize the database
        productSpecificationCharacteristicRelationship.setId(UUID.randomUUID().toString());
        productSpecificationCharacteristicRelationshipRepository.saveAndFlush(productSpecificationCharacteristicRelationship);

        int databaseSizeBeforeDelete = productSpecificationCharacteristicRelationshipRepository.findAll().size();

        // Delete the productSpecificationCharacteristicRelationship
        restProductSpecificationCharacteristicRelationshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, productSpecificationCharacteristicRelationship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSpecificationCharacteristicRelationship> productSpecificationCharacteristicRelationshipList = productSpecificationCharacteristicRelationshipRepository.findAll();
        assertThat(productSpecificationCharacteristicRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

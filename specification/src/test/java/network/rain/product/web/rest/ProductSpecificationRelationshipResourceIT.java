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
import network.rain.product.domain.ProductSpecificationRelationship;
import network.rain.product.repository.ProductSpecificationRelationshipRepository;
import network.rain.product.service.dto.ProductSpecificationRelationshipDTO;
import network.rain.product.service.mapper.ProductSpecificationRelationshipMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductSpecificationRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restProductSpecificationRelationshipMockMvc;

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

    @BeforeEach
    public void initTest() {
        productSpecificationRelationship = createEntity(em);
    }

    @Test
    @Transactional
    void createProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeCreate = productSpecificationRelationshipRepository.findAll().size();
        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );
        restProductSpecificationRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
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
    @Transactional
    void createProductSpecificationRelationshipWithExistingId() throws Exception {
        // Create the ProductSpecificationRelationship with an existing ID
        productSpecificationRelationship.setId("existing_id");
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        int databaseSizeBeforeCreate = productSpecificationRelationshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSpecificationRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductSpecificationRelationships() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.saveAndFlush(productSpecificationRelationship);

        // Get all the productSpecificationRelationshipList
        restProductSpecificationRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSpecificationRelationship.getId())))
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
    void getProductSpecificationRelationship() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.saveAndFlush(productSpecificationRelationship);

        // Get the productSpecificationRelationship
        restProductSpecificationRelationshipMockMvc
            .perform(get(ENTITY_API_URL_ID, productSpecificationRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSpecificationRelationship.getId()))
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
    void getNonExistingProductSpecificationRelationship() throws Exception {
        // Get the productSpecificationRelationship
        restProductSpecificationRelationshipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductSpecificationRelationship() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.saveAndFlush(productSpecificationRelationship);

        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();

        // Update the productSpecificationRelationship
        ProductSpecificationRelationship updatedProductSpecificationRelationship = productSpecificationRelationshipRepository
            .findById(productSpecificationRelationship.getId())
            .get();
        // Disconnect from session so that the updates on updatedProductSpecificationRelationship are not directly saved in db
        em.detach(updatedProductSpecificationRelationship);
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

        restProductSpecificationRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
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
    @Transactional
    void putNonExistingProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSpecificationRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductSpecificationRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.saveAndFlush(productSpecificationRelationship);

        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();

        // Update the productSpecificationRelationship using partial update
        ProductSpecificationRelationship partialUpdatedProductSpecificationRelationship = new ProductSpecificationRelationship();
        partialUpdatedProductSpecificationRelationship.setId(productSpecificationRelationship.getId());

        partialUpdatedProductSpecificationRelationship
            .name(UPDATED_NAME)
            .relationshipType(UPDATED_RELATIONSHIP_TYPE)
            .validForFrom(UPDATED_VALID_FOR_FROM);

        restProductSpecificationRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecificationRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationRelationship))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
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
    @Transactional
    void fullUpdateProductSpecificationRelationshipWithPatch() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.saveAndFlush(productSpecificationRelationship);

        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();

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

        restProductSpecificationRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSpecificationRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSpecificationRelationship))
            )
            .andExpect(status().isOk());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
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
    @Transactional
    void patchNonExistingProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSpecificationRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productSpecificationRelationshipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductSpecificationRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productSpecificationRelationshipRepository.findAll().size();
        productSpecificationRelationship.setId(UUID.randomUUID().toString());

        // Create the ProductSpecificationRelationship
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO = productSpecificationRelationshipMapper.toDto(
            productSpecificationRelationship
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSpecificationRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSpecificationRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSpecificationRelationship in the database
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductSpecificationRelationship() throws Exception {
        // Initialize the database
        productSpecificationRelationship.setId(UUID.randomUUID().toString());
        productSpecificationRelationshipRepository.saveAndFlush(productSpecificationRelationship);

        int databaseSizeBeforeDelete = productSpecificationRelationshipRepository.findAll().size();

        // Delete the productSpecificationRelationship
        restProductSpecificationRelationshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, productSpecificationRelationship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSpecificationRelationship> productSpecificationRelationshipList = productSpecificationRelationshipRepository.findAll();
        assertThat(productSpecificationRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

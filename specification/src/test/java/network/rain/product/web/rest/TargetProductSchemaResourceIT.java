package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.TargetProductSchema;
import network.rain.product.repository.TargetProductSchemaRepository;
import network.rain.product.service.dto.TargetProductSchemaDTO;
import network.rain.product.service.mapper.TargetProductSchemaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TargetProductSchemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TargetProductSchemaResourceIT {

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/target-product-schemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TargetProductSchemaRepository targetProductSchemaRepository;

    @Autowired
    private TargetProductSchemaMapper targetProductSchemaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTargetProductSchemaMockMvc;

    private TargetProductSchema targetProductSchema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetProductSchema createEntity(EntityManager em) {
        TargetProductSchema targetProductSchema = new TargetProductSchema().schemaLocation(DEFAULT_SCHEMA_LOCATION).type(DEFAULT_TYPE);
        return targetProductSchema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetProductSchema createUpdatedEntity(EntityManager em) {
        TargetProductSchema targetProductSchema = new TargetProductSchema().schemaLocation(UPDATED_SCHEMA_LOCATION).type(UPDATED_TYPE);
        return targetProductSchema;
    }

    @BeforeEach
    public void initTest() {
        targetProductSchema = createEntity(em);
    }

    @Test
    @Transactional
    void createTargetProductSchema() throws Exception {
        int databaseSizeBeforeCreate = targetProductSchemaRepository.findAll().size();
        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);
        restTargetProductSchemaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeCreate + 1);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createTargetProductSchemaWithExistingId() throws Exception {
        // Create the TargetProductSchema with an existing ID
        targetProductSchema.setId(1L);
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        int databaseSizeBeforeCreate = targetProductSchemaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTargetProductSchemaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTargetProductSchemas() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.saveAndFlush(targetProductSchema);

        // Get all the targetProductSchemaList
        restTargetProductSchemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(targetProductSchema.getId().intValue())))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getTargetProductSchema() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.saveAndFlush(targetProductSchema);

        // Get the targetProductSchema
        restTargetProductSchemaMockMvc
            .perform(get(ENTITY_API_URL_ID, targetProductSchema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(targetProductSchema.getId().intValue()))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingTargetProductSchema() throws Exception {
        // Get the targetProductSchema
        restTargetProductSchemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTargetProductSchema() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.saveAndFlush(targetProductSchema);

        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();

        // Update the targetProductSchema
        TargetProductSchema updatedTargetProductSchema = targetProductSchemaRepository.findById(targetProductSchema.getId()).get();
        // Disconnect from session so that the updates on updatedTargetProductSchema are not directly saved in db
        em.detach(updatedTargetProductSchema);
        updatedTargetProductSchema.schemaLocation(UPDATED_SCHEMA_LOCATION).type(UPDATED_TYPE);
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(updatedTargetProductSchema);

        restTargetProductSchemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, targetProductSchemaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isOk());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetProductSchemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, targetProductSchemaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetProductSchemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetProductSchemaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTargetProductSchemaWithPatch() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.saveAndFlush(targetProductSchema);

        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();

        // Update the targetProductSchema using partial update
        TargetProductSchema partialUpdatedTargetProductSchema = new TargetProductSchema();
        partialUpdatedTargetProductSchema.setId(targetProductSchema.getId());

        partialUpdatedTargetProductSchema.schemaLocation(UPDATED_SCHEMA_LOCATION);

        restTargetProductSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTargetProductSchema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetProductSchema))
            )
            .andExpect(status().isOk());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTargetProductSchemaWithPatch() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.saveAndFlush(targetProductSchema);

        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();

        // Update the targetProductSchema using partial update
        TargetProductSchema partialUpdatedTargetProductSchema = new TargetProductSchema();
        partialUpdatedTargetProductSchema.setId(targetProductSchema.getId());

        partialUpdatedTargetProductSchema.schemaLocation(UPDATED_SCHEMA_LOCATION).type(UPDATED_TYPE);

        restTargetProductSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTargetProductSchema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetProductSchema))
            )
            .andExpect(status().isOk());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
        TargetProductSchema testTargetProductSchema = targetProductSchemaList.get(targetProductSchemaList.size() - 1);
        assertThat(testTargetProductSchema.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testTargetProductSchema.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetProductSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, targetProductSchemaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetProductSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTargetProductSchema() throws Exception {
        int databaseSizeBeforeUpdate = targetProductSchemaRepository.findAll().size();
        targetProductSchema.setId(count.incrementAndGet());

        // Create the TargetProductSchema
        TargetProductSchemaDTO targetProductSchemaDTO = targetProductSchemaMapper.toDto(targetProductSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetProductSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetProductSchemaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TargetProductSchema in the database
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTargetProductSchema() throws Exception {
        // Initialize the database
        targetProductSchemaRepository.saveAndFlush(targetProductSchema);

        int databaseSizeBeforeDelete = targetProductSchemaRepository.findAll().size();

        // Delete the targetProductSchema
        restTargetProductSchemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, targetProductSchema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TargetProductSchema> targetProductSchemaList = targetProductSchemaRepository.findAll();
        assertThat(targetProductSchemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

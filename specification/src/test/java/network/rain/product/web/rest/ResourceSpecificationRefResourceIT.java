package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.ResourceSpecificationRef;
import network.rain.product.repository.ResourceSpecificationRefRepository;
import network.rain.product.service.dto.ResourceSpecificationRefDTO;
import network.rain.product.service.mapper.ResourceSpecificationRefMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ResourceSpecificationRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResourceSpecificationRefResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resource-specification-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ResourceSpecificationRefRepository resourceSpecificationRefRepository;

    @Autowired
    private ResourceSpecificationRefMapper resourceSpecificationRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourceSpecificationRefMockMvc;

    private ResourceSpecificationRef resourceSpecificationRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceSpecificationRef createEntity(EntityManager em) {
        ResourceSpecificationRef resourceSpecificationRef = new ResourceSpecificationRef()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return resourceSpecificationRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceSpecificationRef createUpdatedEntity(EntityManager em) {
        ResourceSpecificationRef resourceSpecificationRef = new ResourceSpecificationRef()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return resourceSpecificationRef;
    }

    @BeforeEach
    public void initTest() {
        resourceSpecificationRef = createEntity(em);
    }

    @Test
    @Transactional
    void createResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeCreate = resourceSpecificationRefRepository.findAll().size();
        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);
        restResourceSpecificationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createResourceSpecificationRefWithExistingId() throws Exception {
        // Create the ResourceSpecificationRef with an existing ID
        resourceSpecificationRef.setId("existing_id");
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        int databaseSizeBeforeCreate = resourceSpecificationRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceSpecificationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResourceSpecificationRefs() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.saveAndFlush(resourceSpecificationRef);

        // Get all the resourceSpecificationRefList
        restResourceSpecificationRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceSpecificationRef.getId())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getResourceSpecificationRef() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.saveAndFlush(resourceSpecificationRef);

        // Get the resourceSpecificationRef
        restResourceSpecificationRefMockMvc
            .perform(get(ENTITY_API_URL_ID, resourceSpecificationRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resourceSpecificationRef.getId()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingResourceSpecificationRef() throws Exception {
        // Get the resourceSpecificationRef
        restResourceSpecificationRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResourceSpecificationRef() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.saveAndFlush(resourceSpecificationRef);

        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();

        // Update the resourceSpecificationRef
        ResourceSpecificationRef updatedResourceSpecificationRef = resourceSpecificationRefRepository
            .findById(resourceSpecificationRef.getId())
            .get();
        // Disconnect from session so that the updates on updatedResourceSpecificationRef are not directly saved in db
        em.detach(updatedResourceSpecificationRef);
        updatedResourceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(updatedResourceSpecificationRef);

        restResourceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceSpecificationRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceSpecificationRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResourceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.saveAndFlush(resourceSpecificationRef);

        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();

        // Update the resourceSpecificationRef using partial update
        ResourceSpecificationRef partialUpdatedResourceSpecificationRef = new ResourceSpecificationRef();
        partialUpdatedResourceSpecificationRef.setId(resourceSpecificationRef.getId());

        partialUpdatedResourceSpecificationRef.name(UPDATED_NAME);

        restResourceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceSpecificationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceSpecificationRef))
            )
            .andExpect(status().isOk());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateResourceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.saveAndFlush(resourceSpecificationRef);

        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();

        // Update the resourceSpecificationRef using partial update
        ResourceSpecificationRef partialUpdatedResourceSpecificationRef = new ResourceSpecificationRef();
        partialUpdatedResourceSpecificationRef.setId(resourceSpecificationRef.getId());

        partialUpdatedResourceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        restResourceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceSpecificationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceSpecificationRef))
            )
            .andExpect(status().isOk());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ResourceSpecificationRef testResourceSpecificationRef = resourceSpecificationRefList.get(resourceSpecificationRefList.size() - 1);
        assertThat(testResourceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testResourceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResourceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testResourceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testResourceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resourceSpecificationRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResourceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = resourceSpecificationRefRepository.findAll().size();
        resourceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ResourceSpecificationRef
        ResourceSpecificationRefDTO resourceSpecificationRefDTO = resourceSpecificationRefMapper.toDto(resourceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceSpecificationRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceSpecificationRef in the database
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResourceSpecificationRef() throws Exception {
        // Initialize the database
        resourceSpecificationRef.setId(UUID.randomUUID().toString());
        resourceSpecificationRefRepository.saveAndFlush(resourceSpecificationRef);

        int databaseSizeBeforeDelete = resourceSpecificationRefRepository.findAll().size();

        // Delete the resourceSpecificationRef
        restResourceSpecificationRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, resourceSpecificationRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourceSpecificationRef> resourceSpecificationRefList = resourceSpecificationRefRepository.findAll();
        assertThat(resourceSpecificationRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

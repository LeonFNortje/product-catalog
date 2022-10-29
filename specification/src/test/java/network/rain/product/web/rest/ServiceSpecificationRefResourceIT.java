package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.ServiceSpecificationRef;
import network.rain.product.repository.ServiceSpecificationRefRepository;
import network.rain.product.service.dto.ServiceSpecificationRefDTO;
import network.rain.product.service.mapper.ServiceSpecificationRefMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ServiceSpecificationRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceSpecificationRefResourceIT {

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

    private static final String ENTITY_API_URL = "/api/service-specification-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ServiceSpecificationRefRepository serviceSpecificationRefRepository;

    @Autowired
    private ServiceSpecificationRefMapper serviceSpecificationRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceSpecificationRefMockMvc;

    private ServiceSpecificationRef serviceSpecificationRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSpecificationRef createEntity(EntityManager em) {
        ServiceSpecificationRef serviceSpecificationRef = new ServiceSpecificationRef()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return serviceSpecificationRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceSpecificationRef createUpdatedEntity(EntityManager em) {
        ServiceSpecificationRef serviceSpecificationRef = new ServiceSpecificationRef()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return serviceSpecificationRef;
    }

    @BeforeEach
    public void initTest() {
        serviceSpecificationRef = createEntity(em);
    }

    @Test
    @Transactional
    void createServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeCreate = serviceSpecificationRefRepository.findAll().size();
        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);
        restServiceSpecificationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createServiceSpecificationRefWithExistingId() throws Exception {
        // Create the ServiceSpecificationRef with an existing ID
        serviceSpecificationRef.setId("existing_id");
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        int databaseSizeBeforeCreate = serviceSpecificationRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceSpecificationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServiceSpecificationRefs() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.saveAndFlush(serviceSpecificationRef);

        // Get all the serviceSpecificationRefList
        restServiceSpecificationRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceSpecificationRef.getId())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getServiceSpecificationRef() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.saveAndFlush(serviceSpecificationRef);

        // Get the serviceSpecificationRef
        restServiceSpecificationRefMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceSpecificationRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceSpecificationRef.getId()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingServiceSpecificationRef() throws Exception {
        // Get the serviceSpecificationRef
        restServiceSpecificationRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceSpecificationRef() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.saveAndFlush(serviceSpecificationRef);

        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();

        // Update the serviceSpecificationRef
        ServiceSpecificationRef updatedServiceSpecificationRef = serviceSpecificationRefRepository
            .findById(serviceSpecificationRef.getId())
            .get();
        // Disconnect from session so that the updates on updatedServiceSpecificationRef are not directly saved in db
        em.detach(updatedServiceSpecificationRef);
        updatedServiceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(updatedServiceSpecificationRef);

        restServiceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceSpecificationRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceSpecificationRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceSpecificationRefMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.saveAndFlush(serviceSpecificationRef);

        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();

        // Update the serviceSpecificationRef using partial update
        ServiceSpecificationRef partialUpdatedServiceSpecificationRef = new ServiceSpecificationRef();
        partialUpdatedServiceSpecificationRef.setId(serviceSpecificationRef.getId());

        partialUpdatedServiceSpecificationRef.version(UPDATED_VERSION).schemaLocation(UPDATED_SCHEMA_LOCATION);

        restServiceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceSpecificationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceSpecificationRef))
            )
            .andExpect(status().isOk());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateServiceSpecificationRefWithPatch() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.saveAndFlush(serviceSpecificationRef);

        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();

        // Update the serviceSpecificationRef using partial update
        ServiceSpecificationRef partialUpdatedServiceSpecificationRef = new ServiceSpecificationRef();
        partialUpdatedServiceSpecificationRef.setId(serviceSpecificationRef.getId());

        partialUpdatedServiceSpecificationRef
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        restServiceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceSpecificationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceSpecificationRef))
            )
            .andExpect(status().isOk());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
        ServiceSpecificationRef testServiceSpecificationRef = serviceSpecificationRefList.get(serviceSpecificationRefList.size() - 1);
        assertThat(testServiceSpecificationRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testServiceSpecificationRef.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceSpecificationRef.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testServiceSpecificationRef.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testServiceSpecificationRef.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceSpecificationRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceSpecificationRef() throws Exception {
        int databaseSizeBeforeUpdate = serviceSpecificationRefRepository.findAll().size();
        serviceSpecificationRef.setId(UUID.randomUUID().toString());

        // Create the ServiceSpecificationRef
        ServiceSpecificationRefDTO serviceSpecificationRefDTO = serviceSpecificationRefMapper.toDto(serviceSpecificationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceSpecificationRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceSpecificationRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceSpecificationRef in the database
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceSpecificationRef() throws Exception {
        // Initialize the database
        serviceSpecificationRef.setId(UUID.randomUUID().toString());
        serviceSpecificationRefRepository.saveAndFlush(serviceSpecificationRef);

        int databaseSizeBeforeDelete = serviceSpecificationRefRepository.findAll().size();

        // Delete the serviceSpecificationRef
        restServiceSpecificationRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceSpecificationRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceSpecificationRef> serviceSpecificationRefList = serviceSpecificationRefRepository.findAll();
        assertThat(serviceSpecificationRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

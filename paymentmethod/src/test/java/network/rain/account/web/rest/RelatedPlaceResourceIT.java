package network.rain.account.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.account.IntegrationTest;
import network.rain.account.domain.RelatedPlace;
import network.rain.account.repository.RelatedPlaceRepository;
import network.rain.account.service.dto.RelatedPlaceDTO;
import network.rain.account.service.mapper.RelatedPlaceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RelatedPlaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatedPlaceResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/related-places";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RelatedPlaceRepository relatedPlaceRepository;

    @Autowired
    private RelatedPlaceMapper relatedPlaceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatedPlaceMockMvc;

    private RelatedPlace relatedPlace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedPlace createEntity(EntityManager em) {
        RelatedPlace relatedPlace = new RelatedPlace()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .role(DEFAULT_ROLE)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return relatedPlace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedPlace createUpdatedEntity(EntityManager em) {
        RelatedPlace relatedPlace = new RelatedPlace()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return relatedPlace;
    }

    @BeforeEach
    public void initTest() {
        relatedPlace = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatedPlace() throws Exception {
        int databaseSizeBeforeCreate = relatedPlaceRepository.findAll().size();
        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);
        restRelatedPlaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createRelatedPlaceWithExistingId() throws Exception {
        // Create the RelatedPlace with an existing ID
        relatedPlace.setId("existing_id");
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        int databaseSizeBeforeCreate = relatedPlaceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatedPlaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelatedPlaces() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.saveAndFlush(relatedPlace);

        // Get all the relatedPlaceList
        restRelatedPlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatedPlace.getId())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getRelatedPlace() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.saveAndFlush(relatedPlace);

        // Get the relatedPlace
        restRelatedPlaceMockMvc
            .perform(get(ENTITY_API_URL_ID, relatedPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatedPlace.getId()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingRelatedPlace() throws Exception {
        // Get the relatedPlace
        restRelatedPlaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRelatedPlace() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.saveAndFlush(relatedPlace);

        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();

        // Update the relatedPlace
        RelatedPlace updatedRelatedPlace = relatedPlaceRepository.findById(relatedPlace.getId()).get();
        // Disconnect from session so that the updates on updatedRelatedPlace are not directly saved in db
        em.detach(updatedRelatedPlace);
        updatedRelatedPlace
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(updatedRelatedPlace);

        restRelatedPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedPlaceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isOk());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedPlaceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPlaceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatedPlaceWithPatch() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.saveAndFlush(relatedPlace);

        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();

        // Update the relatedPlace using partial update
        RelatedPlace partialUpdatedRelatedPlace = new RelatedPlace();
        partialUpdatedRelatedPlace.setId(relatedPlace.getId());

        partialUpdatedRelatedPlace.name(UPDATED_NAME).role(UPDATED_ROLE);

        restRelatedPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedPlace.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedPlace))
            )
            .andExpect(status().isOk());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateRelatedPlaceWithPatch() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.saveAndFlush(relatedPlace);

        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();

        // Update the relatedPlace using partial update
        RelatedPlace partialUpdatedRelatedPlace = new RelatedPlace();
        partialUpdatedRelatedPlace.setId(relatedPlace.getId());

        partialUpdatedRelatedPlace
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        restRelatedPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedPlace.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedPlace))
            )
            .andExpect(status().isOk());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
        RelatedPlace testRelatedPlace = relatedPlaceList.get(relatedPlaceList.size() - 1);
        assertThat(testRelatedPlace.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedPlace.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedPlace.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedPlace.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relatedPlaceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelatedPlace() throws Exception {
        int databaseSizeBeforeUpdate = relatedPlaceRepository.findAll().size();
        relatedPlace.setId(UUID.randomUUID().toString());

        // Create the RelatedPlace
        RelatedPlaceDTO relatedPlaceDTO = relatedPlaceMapper.toDto(relatedPlace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedPlaceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedPlace in the database
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelatedPlace() throws Exception {
        // Initialize the database
        relatedPlace.setId(UUID.randomUUID().toString());
        relatedPlaceRepository.saveAndFlush(relatedPlace);

        int databaseSizeBeforeDelete = relatedPlaceRepository.findAll().size();

        // Delete the relatedPlace
        restRelatedPlaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatedPlace.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelatedPlace> relatedPlaceList = relatedPlaceRepository.findAll();
        assertThat(relatedPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

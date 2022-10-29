package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.RelatedParty;
import network.rain.product.repository.RelatedPartyRepository;
import network.rain.product.service.dto.RelatedPartyDTO;
import network.rain.product.service.mapper.RelatedPartyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RelatedPartyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatedPartyResourceIT {

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

    private static final String ENTITY_API_URL = "/api/related-parties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RelatedPartyRepository relatedPartyRepository;

    @Autowired
    private RelatedPartyMapper relatedPartyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatedPartyMockMvc;

    private RelatedParty relatedParty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedParty createEntity(EntityManager em) {
        RelatedParty relatedParty = new RelatedParty()
            .href(DEFAULT_HREF)
            .name(DEFAULT_NAME)
            .role(DEFAULT_ROLE)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return relatedParty;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedParty createUpdatedEntity(EntityManager em) {
        RelatedParty relatedParty = new RelatedParty()
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return relatedParty;
    }

    @BeforeEach
    public void initTest() {
        relatedParty = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatedParty() throws Exception {
        int databaseSizeBeforeCreate = relatedPartyRepository.findAll().size();
        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);
        restRelatedPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeCreate + 1);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createRelatedPartyWithExistingId() throws Exception {
        // Create the RelatedParty with an existing ID
        relatedParty.setId("existing_id");
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        int databaseSizeBeforeCreate = relatedPartyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatedPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelatedParties() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.saveAndFlush(relatedParty);

        // Get all the relatedPartyList
        restRelatedPartyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatedParty.getId())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getRelatedParty() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.saveAndFlush(relatedParty);

        // Get the relatedParty
        restRelatedPartyMockMvc
            .perform(get(ENTITY_API_URL_ID, relatedParty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatedParty.getId()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingRelatedParty() throws Exception {
        // Get the relatedParty
        restRelatedPartyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRelatedParty() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.saveAndFlush(relatedParty);

        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();

        // Update the relatedParty
        RelatedParty updatedRelatedParty = relatedPartyRepository.findById(relatedParty.getId()).get();
        // Disconnect from session so that the updates on updatedRelatedParty are not directly saved in db
        em.detach(updatedRelatedParty);
        updatedRelatedParty
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(updatedRelatedParty);

        restRelatedPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedPartyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isOk());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedPartyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPartyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatedPartyWithPatch() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.saveAndFlush(relatedParty);

        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();

        // Update the relatedParty using partial update
        RelatedParty partialUpdatedRelatedParty = new RelatedParty();
        partialUpdatedRelatedParty.setId(relatedParty.getId());

        partialUpdatedRelatedParty.href(UPDATED_HREF);

        restRelatedPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedParty.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedParty))
            )
            .andExpect(status().isOk());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateRelatedPartyWithPatch() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.saveAndFlush(relatedParty);

        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();

        // Update the relatedParty using partial update
        RelatedParty partialUpdatedRelatedParty = new RelatedParty();
        partialUpdatedRelatedParty.setId(relatedParty.getId());

        partialUpdatedRelatedParty
            .href(UPDATED_HREF)
            .name(UPDATED_NAME)
            .role(UPDATED_ROLE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        restRelatedPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedParty.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedParty))
            )
            .andExpect(status().isOk());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
        RelatedParty testRelatedParty = relatedPartyList.get(relatedPartyList.size() - 1);
        assertThat(testRelatedParty.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testRelatedParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelatedParty.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRelatedParty.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testRelatedParty.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relatedPartyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelatedParty() throws Exception {
        int databaseSizeBeforeUpdate = relatedPartyRepository.findAll().size();
        relatedParty.setId(UUID.randomUUID().toString());

        // Create the RelatedParty
        RelatedPartyDTO relatedPartyDTO = relatedPartyMapper.toDto(relatedParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedPartyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedPartyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedParty in the database
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelatedParty() throws Exception {
        // Initialize the database
        relatedParty.setId(UUID.randomUUID().toString());
        relatedPartyRepository.saveAndFlush(relatedParty);

        int databaseSizeBeforeDelete = relatedPartyRepository.findAll().size();

        // Delete the relatedParty
        restRelatedPartyMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatedParty.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelatedParty> relatedPartyList = relatedPartyRepository.findAll();
        assertThat(relatedPartyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

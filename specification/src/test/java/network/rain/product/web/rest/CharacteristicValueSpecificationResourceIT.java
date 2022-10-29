package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.CharacteristicValueSpecification;
import network.rain.product.repository.CharacteristicValueSpecificationRepository;
import network.rain.product.service.dto.CharacteristicValueSpecificationDTO;
import network.rain.product.service.mapper.CharacteristicValueSpecificationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CharacteristicValueSpecificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CharacteristicValueSpecificationResourceIT {

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final String DEFAULT_RANGE_INTERVAL = "AAAAAAAAAA";
    private static final String UPDATED_RANGE_INTERVAL = "BBBBBBBBBB";

    private static final String DEFAULT_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_REGEX = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_OF_MEASURE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASURE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/characteristic-value-specifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CharacteristicValueSpecificationRepository characteristicValueSpecificationRepository;

    @Autowired
    private CharacteristicValueSpecificationMapper characteristicValueSpecificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCharacteristicValueSpecificationMockMvc;

    private CharacteristicValueSpecification characteristicValueSpecification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CharacteristicValueSpecification createEntity(EntityManager em) {
        CharacteristicValueSpecification characteristicValueSpecification = new CharacteristicValueSpecification()
            .isDefault(DEFAULT_IS_DEFAULT)
            .rangeInterval(DEFAULT_RANGE_INTERVAL)
            .regex(DEFAULT_REGEX)
            .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE)
            .validForFrom(DEFAULT_VALID_FOR_FROM)
            .validForTo(DEFAULT_VALID_FOR_TO)
            .valueType(DEFAULT_VALUE_TYPE)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return characteristicValueSpecification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CharacteristicValueSpecification createUpdatedEntity(EntityManager em) {
        CharacteristicValueSpecification characteristicValueSpecification = new CharacteristicValueSpecification()
            .isDefault(UPDATED_IS_DEFAULT)
            .rangeInterval(UPDATED_RANGE_INTERVAL)
            .regex(UPDATED_REGEX)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return characteristicValueSpecification;
    }

    @BeforeEach
    public void initTest() {
        characteristicValueSpecification = createEntity(em);
    }

    @Test
    @Transactional
    void createCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeCreate = characteristicValueSpecificationRepository.findAll().size();
        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );
        restCharacteristicValueSpecificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeCreate + 1);
        CharacteristicValueSpecification testCharacteristicValueSpecification = characteristicValueSpecificationList.get(
            characteristicValueSpecificationList.size() - 1
        );
        assertThat(testCharacteristicValueSpecification.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testCharacteristicValueSpecification.getRangeInterval()).isEqualTo(DEFAULT_RANGE_INTERVAL);
        assertThat(testCharacteristicValueSpecification.getRegex()).isEqualTo(DEFAULT_REGEX);
        assertThat(testCharacteristicValueSpecification.getUnitOfMeasure()).isEqualTo(DEFAULT_UNIT_OF_MEASURE);
        assertThat(testCharacteristicValueSpecification.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testCharacteristicValueSpecification.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testCharacteristicValueSpecification.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCharacteristicValueSpecification.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testCharacteristicValueSpecification.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createCharacteristicValueSpecificationWithExistingId() throws Exception {
        // Create the CharacteristicValueSpecification with an existing ID
        characteristicValueSpecification.setId(1L);
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        int databaseSizeBeforeCreate = characteristicValueSpecificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacteristicValueSpecificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCharacteristicValueSpecifications() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.saveAndFlush(characteristicValueSpecification);

        // Get all the characteristicValueSpecificationList
        restCharacteristicValueSpecificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characteristicValueSpecification.getId().intValue())))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].rangeInterval").value(hasItem(DEFAULT_RANGE_INTERVAL)))
            .andExpect(jsonPath("$.[*].regex").value(hasItem(DEFAULT_REGEX)))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE)))
            .andExpect(jsonPath("$.[*].validForFrom").value(hasItem(DEFAULT_VALID_FOR_FROM.toString())))
            .andExpect(jsonPath("$.[*].validForTo").value(hasItem(DEFAULT_VALID_FOR_TO.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getCharacteristicValueSpecification() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.saveAndFlush(characteristicValueSpecification);

        // Get the characteristicValueSpecification
        restCharacteristicValueSpecificationMockMvc
            .perform(get(ENTITY_API_URL_ID, characteristicValueSpecification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(characteristicValueSpecification.getId().intValue()))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.rangeInterval").value(DEFAULT_RANGE_INTERVAL))
            .andExpect(jsonPath("$.regex").value(DEFAULT_REGEX))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE))
            .andExpect(jsonPath("$.validForFrom").value(DEFAULT_VALID_FOR_FROM.toString()))
            .andExpect(jsonPath("$.validForTo").value(DEFAULT_VALID_FOR_TO.toString()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingCharacteristicValueSpecification() throws Exception {
        // Get the characteristicValueSpecification
        restCharacteristicValueSpecificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCharacteristicValueSpecification() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.saveAndFlush(characteristicValueSpecification);

        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();

        // Update the characteristicValueSpecification
        CharacteristicValueSpecification updatedCharacteristicValueSpecification = characteristicValueSpecificationRepository
            .findById(characteristicValueSpecification.getId())
            .get();
        // Disconnect from session so that the updates on updatedCharacteristicValueSpecification are not directly saved in db
        em.detach(updatedCharacteristicValueSpecification);
        updatedCharacteristicValueSpecification
            .isDefault(UPDATED_IS_DEFAULT)
            .rangeInterval(UPDATED_RANGE_INTERVAL)
            .regex(UPDATED_REGEX)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            updatedCharacteristicValueSpecification
        );

        restCharacteristicValueSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, characteristicValueSpecificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
        CharacteristicValueSpecification testCharacteristicValueSpecification = characteristicValueSpecificationList.get(
            characteristicValueSpecificationList.size() - 1
        );
        assertThat(testCharacteristicValueSpecification.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testCharacteristicValueSpecification.getRangeInterval()).isEqualTo(UPDATED_RANGE_INTERVAL);
        assertThat(testCharacteristicValueSpecification.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testCharacteristicValueSpecification.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testCharacteristicValueSpecification.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testCharacteristicValueSpecification.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testCharacteristicValueSpecification.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCharacteristicValueSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testCharacteristicValueSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacteristicValueSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, characteristicValueSpecificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacteristicValueSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacteristicValueSpecificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCharacteristicValueSpecificationWithPatch() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.saveAndFlush(characteristicValueSpecification);

        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();

        // Update the characteristicValueSpecification using partial update
        CharacteristicValueSpecification partialUpdatedCharacteristicValueSpecification = new CharacteristicValueSpecification();
        partialUpdatedCharacteristicValueSpecification.setId(characteristicValueSpecification.getId());

        partialUpdatedCharacteristicValueSpecification
            .rangeInterval(UPDATED_RANGE_INTERVAL)
            .regex(UPDATED_REGEX)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .valueType(UPDATED_VALUE_TYPE);

        restCharacteristicValueSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharacteristicValueSpecification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacteristicValueSpecification))
            )
            .andExpect(status().isOk());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
        CharacteristicValueSpecification testCharacteristicValueSpecification = characteristicValueSpecificationList.get(
            characteristicValueSpecificationList.size() - 1
        );
        assertThat(testCharacteristicValueSpecification.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testCharacteristicValueSpecification.getRangeInterval()).isEqualTo(UPDATED_RANGE_INTERVAL);
        assertThat(testCharacteristicValueSpecification.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testCharacteristicValueSpecification.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testCharacteristicValueSpecification.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testCharacteristicValueSpecification.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testCharacteristicValueSpecification.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCharacteristicValueSpecification.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testCharacteristicValueSpecification.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCharacteristicValueSpecificationWithPatch() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.saveAndFlush(characteristicValueSpecification);

        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();

        // Update the characteristicValueSpecification using partial update
        CharacteristicValueSpecification partialUpdatedCharacteristicValueSpecification = new CharacteristicValueSpecification();
        partialUpdatedCharacteristicValueSpecification.setId(characteristicValueSpecification.getId());

        partialUpdatedCharacteristicValueSpecification
            .isDefault(UPDATED_IS_DEFAULT)
            .rangeInterval(UPDATED_RANGE_INTERVAL)
            .regex(UPDATED_REGEX)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .valueType(UPDATED_VALUE_TYPE)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        restCharacteristicValueSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharacteristicValueSpecification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacteristicValueSpecification))
            )
            .andExpect(status().isOk());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
        CharacteristicValueSpecification testCharacteristicValueSpecification = characteristicValueSpecificationList.get(
            characteristicValueSpecificationList.size() - 1
        );
        assertThat(testCharacteristicValueSpecification.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testCharacteristicValueSpecification.getRangeInterval()).isEqualTo(UPDATED_RANGE_INTERVAL);
        assertThat(testCharacteristicValueSpecification.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testCharacteristicValueSpecification.getUnitOfMeasure()).isEqualTo(UPDATED_UNIT_OF_MEASURE);
        assertThat(testCharacteristicValueSpecification.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testCharacteristicValueSpecification.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testCharacteristicValueSpecification.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCharacteristicValueSpecification.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testCharacteristicValueSpecification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacteristicValueSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, characteristicValueSpecificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacteristicValueSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCharacteristicValueSpecificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCharacteristicValueSpecification() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.saveAndFlush(characteristicValueSpecification);

        int databaseSizeBeforeDelete = characteristicValueSpecificationRepository.findAll().size();

        // Delete the characteristicValueSpecification
        restCharacteristicValueSpecificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, characteristicValueSpecification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository.findAll();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

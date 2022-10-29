package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.CharacteristicValueSpecification;
import network.rain.product.repository.CharacteristicValueSpecificationRepository;
import network.rain.product.repository.EntityManager;
import network.rain.product.service.dto.CharacteristicValueSpecificationDTO;
import network.rain.product.service.mapper.CharacteristicValueSpecificationMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link CharacteristicValueSpecificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
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
    private WebTestClient webTestClient;

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

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(CharacteristicValueSpecification.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        characteristicValueSpecification = createEntity(em);
    }

    @Test
    void createCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeCreate = characteristicValueSpecificationRepository.findAll().collectList().block().size();
        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void createCharacteristicValueSpecificationWithExistingId() throws Exception {
        // Create the CharacteristicValueSpecification with an existing ID
        characteristicValueSpecification.setId(1L);
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        int databaseSizeBeforeCreate = characteristicValueSpecificationRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCharacteristicValueSpecifications() {
        // Initialize the database
        characteristicValueSpecificationRepository.save(characteristicValueSpecification).block();

        // Get all the characteristicValueSpecificationList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(characteristicValueSpecification.getId().intValue()))
            .jsonPath("$.[*].isDefault")
            .value(hasItem(DEFAULT_IS_DEFAULT.booleanValue()))
            .jsonPath("$.[*].rangeInterval")
            .value(hasItem(DEFAULT_RANGE_INTERVAL))
            .jsonPath("$.[*].regex")
            .value(hasItem(DEFAULT_REGEX))
            .jsonPath("$.[*].unitOfMeasure")
            .value(hasItem(DEFAULT_UNIT_OF_MEASURE))
            .jsonPath("$.[*].validForFrom")
            .value(hasItem(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.[*].validForTo")
            .value(hasItem(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.[*].valueType")
            .value(hasItem(DEFAULT_VALUE_TYPE))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getCharacteristicValueSpecification() {
        // Initialize the database
        characteristicValueSpecificationRepository.save(characteristicValueSpecification).block();

        // Get the characteristicValueSpecification
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, characteristicValueSpecification.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(characteristicValueSpecification.getId().intValue()))
            .jsonPath("$.isDefault")
            .value(is(DEFAULT_IS_DEFAULT.booleanValue()))
            .jsonPath("$.rangeInterval")
            .value(is(DEFAULT_RANGE_INTERVAL))
            .jsonPath("$.regex")
            .value(is(DEFAULT_REGEX))
            .jsonPath("$.unitOfMeasure")
            .value(is(DEFAULT_UNIT_OF_MEASURE))
            .jsonPath("$.validForFrom")
            .value(is(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.validForTo")
            .value(is(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.valueType")
            .value(is(DEFAULT_VALUE_TYPE))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingCharacteristicValueSpecification() {
        // Get the characteristicValueSpecification
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCharacteristicValueSpecification() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.save(characteristicValueSpecification).block();

        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();

        // Update the characteristicValueSpecification
        CharacteristicValueSpecification updatedCharacteristicValueSpecification = characteristicValueSpecificationRepository
            .findById(characteristicValueSpecification.getId())
            .block();
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

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, characteristicValueSpecificationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void putNonExistingCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, characteristicValueSpecificationDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCharacteristicValueSpecificationWithPatch() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.save(characteristicValueSpecification).block();

        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();

        // Update the characteristicValueSpecification using partial update
        CharacteristicValueSpecification partialUpdatedCharacteristicValueSpecification = new CharacteristicValueSpecification();
        partialUpdatedCharacteristicValueSpecification.setId(characteristicValueSpecification.getId());

        partialUpdatedCharacteristicValueSpecification
            .rangeInterval(UPDATED_RANGE_INTERVAL)
            .regex(UPDATED_REGEX)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .valueType(UPDATED_VALUE_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCharacteristicValueSpecification.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacteristicValueSpecification))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void fullUpdateCharacteristicValueSpecificationWithPatch() throws Exception {
        // Initialize the database
        characteristicValueSpecificationRepository.save(characteristicValueSpecification).block();

        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();

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

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCharacteristicValueSpecification.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCharacteristicValueSpecification))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
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
    void patchNonExistingCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, characteristicValueSpecificationDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCharacteristicValueSpecification() throws Exception {
        int databaseSizeBeforeUpdate = characteristicValueSpecificationRepository.findAll().collectList().block().size();
        characteristicValueSpecification.setId(count.incrementAndGet());

        // Create the CharacteristicValueSpecification
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO = characteristicValueSpecificationMapper.toDto(
            characteristicValueSpecification
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(characteristicValueSpecificationDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CharacteristicValueSpecification in the database
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCharacteristicValueSpecification() {
        // Initialize the database
        characteristicValueSpecificationRepository.save(characteristicValueSpecification).block();

        int databaseSizeBeforeDelete = characteristicValueSpecificationRepository.findAll().collectList().block().size();

        // Delete the characteristicValueSpecification
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, characteristicValueSpecification.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<CharacteristicValueSpecification> characteristicValueSpecificationList = characteristicValueSpecificationRepository
            .findAll()
            .collectList()
            .block();
        assertThat(characteristicValueSpecificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

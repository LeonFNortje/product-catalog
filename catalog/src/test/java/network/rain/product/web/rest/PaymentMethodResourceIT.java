package network.rain.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import network.rain.product.IntegrationTest;
import network.rain.product.domain.PaymentMethod;
import network.rain.product.repository.EntityManager;
import network.rain.product.repository.PaymentMethodRepository;
import network.rain.product.service.dto.PaymentMethodDTO;
import network.rain.product.service.mapper.PaymentMethodMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link PaymentMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PaymentMethodResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORIZATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORIZATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PREFERRED = false;
    private static final Boolean UPDATED_IS_PREFERRED = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS_REASON = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FOR_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_FOR_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FOR_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SCHEMA_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private PaymentMethod paymentMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createEntity(EntityManager em) {
        PaymentMethod paymentMethod = new PaymentMethod()
            .href(DEFAULT_HREF)
            .authorizationCode(DEFAULT_AUTHORIZATION_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isPreferred(DEFAULT_IS_PREFERRED)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .statusDate(DEFAULT_STATUS_DATE)
            .statusReason(DEFAULT_STATUS_REASON)
            .validForFrom(DEFAULT_VALID_FOR_FROM)
            .validForTo(DEFAULT_VALID_FOR_TO)
            .schemaLocation(DEFAULT_SCHEMA_LOCATION)
            .type(DEFAULT_TYPE);
        return paymentMethod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethod createUpdatedEntity(EntityManager em) {
        PaymentMethod paymentMethod = new PaymentMethod()
            .href(UPDATED_HREF)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .description(UPDATED_DESCRIPTION)
            .isPreferred(UPDATED_IS_PREFERRED)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .statusReason(UPDATED_STATUS_REASON)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        return paymentMethod;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(PaymentMethod.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        paymentMethod = createEntity(em);
    }

    @Test
    void createPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().collectList().block().size();
        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testPaymentMethod.getAuthorizationCode()).isEqualTo(DEFAULT_AUTHORIZATION_CODE);
        assertThat(testPaymentMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentMethod.getIsPreferred()).isEqualTo(DEFAULT_IS_PREFERRED);
        assertThat(testPaymentMethod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentMethod.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentMethod.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testPaymentMethod.getStatusReason()).isEqualTo(DEFAULT_STATUS_REASON);
        assertThat(testPaymentMethod.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testPaymentMethod.getValidForTo()).isEqualTo(DEFAULT_VALID_FOR_TO);
        assertThat(testPaymentMethod.getSchemaLocation()).isEqualTo(DEFAULT_SCHEMA_LOCATION);
        assertThat(testPaymentMethod.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createPaymentMethodWithExistingId() throws Exception {
        // Create the PaymentMethod with an existing ID
        paymentMethod.setId("existing_id");
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPaymentMethods() {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.save(paymentMethod).block();

        // Get all the paymentMethodList
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
            .value(hasItem(paymentMethod.getId()))
            .jsonPath("$.[*].href")
            .value(hasItem(DEFAULT_HREF))
            .jsonPath("$.[*].authorizationCode")
            .value(hasItem(DEFAULT_AUTHORIZATION_CODE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].isPreferred")
            .value(hasItem(DEFAULT_IS_PREFERRED.booleanValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS))
            .jsonPath("$.[*].statusDate")
            .value(hasItem(DEFAULT_STATUS_DATE.toString()))
            .jsonPath("$.[*].statusReason")
            .value(hasItem(DEFAULT_STATUS_REASON))
            .jsonPath("$.[*].validForFrom")
            .value(hasItem(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.[*].validForTo")
            .value(hasItem(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.[*].schemaLocation")
            .value(hasItem(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE));
    }

    @Test
    void getPaymentMethod() {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.save(paymentMethod).block();

        // Get the paymentMethod
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, paymentMethod.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(paymentMethod.getId()))
            .jsonPath("$.href")
            .value(is(DEFAULT_HREF))
            .jsonPath("$.authorizationCode")
            .value(is(DEFAULT_AUTHORIZATION_CODE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.isPreferred")
            .value(is(DEFAULT_IS_PREFERRED.booleanValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.status")
            .value(is(DEFAULT_STATUS))
            .jsonPath("$.statusDate")
            .value(is(DEFAULT_STATUS_DATE.toString()))
            .jsonPath("$.statusReason")
            .value(is(DEFAULT_STATUS_REASON))
            .jsonPath("$.validForFrom")
            .value(is(DEFAULT_VALID_FOR_FROM.toString()))
            .jsonPath("$.validForTo")
            .value(is(DEFAULT_VALID_FOR_TO.toString()))
            .jsonPath("$.schemaLocation")
            .value(is(DEFAULT_SCHEMA_LOCATION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingPaymentMethod() {
        // Get the paymentMethod
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPaymentMethod() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.save(paymentMethod).block();

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();

        // Update the paymentMethod
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.findById(paymentMethod.getId()).block();
        updatedPaymentMethod
            .href(UPDATED_HREF)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .description(UPDATED_DESCRIPTION)
            .isPreferred(UPDATED_IS_PREFERRED)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .statusReason(UPDATED_STATUS_REASON)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(updatedPaymentMethod);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, paymentMethodDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testPaymentMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentMethod.getIsPreferred()).isEqualTo(UPDATED_IS_PREFERRED);
        assertThat(testPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentMethod.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testPaymentMethod.getStatusReason()).isEqualTo(UPDATED_STATUS_REASON);
        assertThat(testPaymentMethod.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testPaymentMethod.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testPaymentMethod.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, paymentMethodDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.save(paymentMethod).block();

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod
            .href(UPDATED_HREF)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .statusDate(UPDATED_STATUS_DATE)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testPaymentMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaymentMethod.getIsPreferred()).isEqualTo(DEFAULT_IS_PREFERRED);
        assertThat(testPaymentMethod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentMethod.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentMethod.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testPaymentMethod.getStatusReason()).isEqualTo(DEFAULT_STATUS_REASON);
        assertThat(testPaymentMethod.getValidForFrom()).isEqualTo(DEFAULT_VALID_FOR_FROM);
        assertThat(testPaymentMethod.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testPaymentMethod.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testPaymentMethod.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.save(paymentMethod).block();

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod
            .href(UPDATED_HREF)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .description(UPDATED_DESCRIPTION)
            .isPreferred(UPDATED_IS_PREFERRED)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .statusReason(UPDATED_STATUS_REASON)
            .validForFrom(UPDATED_VALID_FOR_FROM)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION)
            .type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethod testPaymentMethod = paymentMethodList.get(paymentMethodList.size() - 1);
        assertThat(testPaymentMethod.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testPaymentMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaymentMethod.getIsPreferred()).isEqualTo(UPDATED_IS_PREFERRED);
        assertThat(testPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentMethod.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testPaymentMethod.getStatusReason()).isEqualTo(UPDATED_STATUS_REASON);
        assertThat(testPaymentMethod.getValidForFrom()).isEqualTo(UPDATED_VALID_FOR_FROM);
        assertThat(testPaymentMethod.getValidForTo()).isEqualTo(UPDATED_VALID_FOR_TO);
        assertThat(testPaymentMethod.getSchemaLocation()).isEqualTo(UPDATED_SCHEMA_LOCATION);
        assertThat(testPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, paymentMethodDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().collectList().block().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePaymentMethod() {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.save(paymentMethod).block();

        int databaseSizeBeforeDelete = paymentMethodRepository.findAll().collectList().block().size();

        // Delete the paymentMethod
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, paymentMethod.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll().collectList().block();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package network.rain.account.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import network.rain.account.IntegrationTest;
import network.rain.account.domain.PaymentMethod;
import network.rain.account.repository.PaymentMethodRepository;
import network.rain.account.service.dto.PaymentMethodDTO;
import network.rain.account.service.mapper.PaymentMethodMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
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
    private MockMvc restPaymentMethodMockMvc;

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

    @BeforeEach
    public void initTest() {
        paymentMethod = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().size();
        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);
        restPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
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
    @Transactional
    void createPaymentMethodWithExistingId() throws Exception {
        // Create the PaymentMethod with an existing ID
        paymentMethod.setId("existing_id");
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        int databaseSizeBeforeCreate = paymentMethodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get all the paymentMethodList
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethod.getId())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].authorizationCode").value(hasItem(DEFAULT_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isPreferred").value(hasItem(DEFAULT_IS_PREFERRED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].statusReason").value(hasItem(DEFAULT_STATUS_REASON)))
            .andExpect(jsonPath("$.[*].validForFrom").value(hasItem(DEFAULT_VALID_FOR_FROM.toString())))
            .andExpect(jsonPath("$.[*].validForTo").value(hasItem(DEFAULT_VALID_FOR_TO.toString())))
            .andExpect(jsonPath("$.[*].schemaLocation").value(hasItem(DEFAULT_SCHEMA_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getPaymentMethod() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.saveAndFlush(paymentMethod);

        // Get the paymentMethod
        restPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethod.getId()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.authorizationCode").value(DEFAULT_AUTHORIZATION_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isPreferred").value(DEFAULT_IS_PREFERRED.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.statusReason").value(DEFAULT_STATUS_REASON))
            .andExpect(jsonPath("$.validForFrom").value(DEFAULT_VALID_FOR_FROM.toString()))
            .andExpect(jsonPath("$.validForTo").value(DEFAULT_VALID_FOR_TO.toString()))
            .andExpect(jsonPath("$.schemaLocation").value(DEFAULT_SCHEMA_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingPaymentMethod() throws Exception {
        // Get the paymentMethod
        restPaymentMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentMethod() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.findById(paymentMethod.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMethod are not directly saved in db
        em.detach(updatedPaymentMethod);
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

        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentMethodDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
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
    @Transactional
    void putNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentMethodDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

        // Update the paymentMethod using partial update
        PaymentMethod partialUpdatedPaymentMethod = new PaymentMethod();
        partialUpdatedPaymentMethod.setId(paymentMethod.getId());

        partialUpdatedPaymentMethod
            .href(UPDATED_HREF)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .statusDate(UPDATED_STATUS_DATE)
            .validForTo(UPDATED_VALID_FOR_TO)
            .schemaLocation(UPDATED_SCHEMA_LOCATION);

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
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
    @Transactional
    void fullUpdatePaymentMethodWithPatch() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();

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

        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentMethod.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
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
    @Transactional
    void patchNonExistingPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentMethodDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodRepository.findAll().size();
        paymentMethod.setId(UUID.randomUUID().toString());

        // Create the PaymentMethod
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDto(paymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentMethod in the database
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentMethod() throws Exception {
        // Initialize the database
        paymentMethod.setId(UUID.randomUUID().toString());
        paymentMethodRepository.saveAndFlush(paymentMethod);

        int databaseSizeBeforeDelete = paymentMethodRepository.findAll().size();

        // Delete the paymentMethod
        restPaymentMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentMethod.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAll();
        assertThat(paymentMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package network.rain.account.service;

import java.util.Optional;
import network.rain.account.service.dto.PaymentMethodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link network.rain.account.domain.PaymentMethod}.
 */
public interface PaymentMethodService {
    /**
     * Save a paymentMethod.
     *
     * @param paymentMethodDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO);

    /**
     * Updates a paymentMethod.
     *
     * @param paymentMethodDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentMethodDTO update(PaymentMethodDTO paymentMethodDTO);

    /**
     * Partially updates a paymentMethod.
     *
     * @param paymentMethodDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentMethodDTO> partialUpdate(PaymentMethodDTO paymentMethodDTO);

    /**
     * Get all the paymentMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentMethodDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentMethodDTO> findOne(String id);

    /**
     * Delete the "id" paymentMethod.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

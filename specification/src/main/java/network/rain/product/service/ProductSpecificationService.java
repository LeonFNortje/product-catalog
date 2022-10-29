package network.rain.product.service;

import java.util.Optional;
import network.rain.product.service.dto.ProductSpecificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link network.rain.product.domain.ProductSpecification}.
 */
public interface ProductSpecificationService {
    /**
     * Save a productSpecification.
     *
     * @param productSpecificationDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSpecificationDTO save(ProductSpecificationDTO productSpecificationDTO);

    /**
     * Updates a productSpecification.
     *
     * @param productSpecificationDTO the entity to update.
     * @return the persisted entity.
     */
    ProductSpecificationDTO update(ProductSpecificationDTO productSpecificationDTO);

    /**
     * Partially updates a productSpecification.
     *
     * @param productSpecificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductSpecificationDTO> partialUpdate(ProductSpecificationDTO productSpecificationDTO);

    /**
     * Get all the productSpecifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductSpecificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productSpecification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSpecificationDTO> findOne(String id);

    /**
     * Delete the "id" productSpecification.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

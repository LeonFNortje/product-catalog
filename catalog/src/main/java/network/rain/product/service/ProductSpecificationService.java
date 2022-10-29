package network.rain.product.service;

import network.rain.product.service.dto.ProductSpecificationDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    Mono<ProductSpecificationDTO> save(ProductSpecificationDTO productSpecificationDTO);

    /**
     * Updates a productSpecification.
     *
     * @param productSpecificationDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ProductSpecificationDTO> update(ProductSpecificationDTO productSpecificationDTO);

    /**
     * Partially updates a productSpecification.
     *
     * @param productSpecificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ProductSpecificationDTO> partialUpdate(ProductSpecificationDTO productSpecificationDTO);

    /**
     * Get all the productSpecifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<ProductSpecificationDTO> findAll(Pageable pageable);

    /**
     * Returns the number of productSpecifications available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" productSpecification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ProductSpecificationDTO> findOne(String id);

    /**
     * Delete the "id" productSpecification.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}

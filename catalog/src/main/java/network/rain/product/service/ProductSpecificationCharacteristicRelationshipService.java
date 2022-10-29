package network.rain.product.service;

import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import network.rain.product.repository.ProductSpecificationCharacteristicRelationshipRepository;
import network.rain.product.service.dto.ProductSpecificationCharacteristicRelationshipDTO;
import network.rain.product.service.mapper.ProductSpecificationCharacteristicRelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ProductSpecificationCharacteristicRelationship}.
 */
@Service
@Transactional
public class ProductSpecificationCharacteristicRelationshipService {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationCharacteristicRelationshipService.class);

    private final ProductSpecificationCharacteristicRelationshipRepository productSpecificationCharacteristicRelationshipRepository;

    private final ProductSpecificationCharacteristicRelationshipMapper productSpecificationCharacteristicRelationshipMapper;

    public ProductSpecificationCharacteristicRelationshipService(
        ProductSpecificationCharacteristicRelationshipRepository productSpecificationCharacteristicRelationshipRepository,
        ProductSpecificationCharacteristicRelationshipMapper productSpecificationCharacteristicRelationshipMapper
    ) {
        this.productSpecificationCharacteristicRelationshipRepository = productSpecificationCharacteristicRelationshipRepository;
        this.productSpecificationCharacteristicRelationshipMapper = productSpecificationCharacteristicRelationshipMapper;
    }

    /**
     * Save a productSpecificationCharacteristicRelationship.
     *
     * @param productSpecificationCharacteristicRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationCharacteristicRelationshipDTO> save(
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO
    ) {
        log.debug("Request to save ProductSpecificationCharacteristicRelationship : {}", productSpecificationCharacteristicRelationshipDTO);
        return productSpecificationCharacteristicRelationshipRepository
            .save(productSpecificationCharacteristicRelationshipMapper.toEntity(productSpecificationCharacteristicRelationshipDTO))
            .map(productSpecificationCharacteristicRelationshipMapper::toDto);
    }

    /**
     * Update a productSpecificationCharacteristicRelationship.
     *
     * @param productSpecificationCharacteristicRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationCharacteristicRelationshipDTO> update(
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO
    ) {
        log.debug(
            "Request to update ProductSpecificationCharacteristicRelationship : {}",
            productSpecificationCharacteristicRelationshipDTO
        );
        return productSpecificationCharacteristicRelationshipRepository
            .save(
                productSpecificationCharacteristicRelationshipMapper
                    .toEntity(productSpecificationCharacteristicRelationshipDTO)
                    .setIsPersisted()
            )
            .map(productSpecificationCharacteristicRelationshipMapper::toDto);
    }

    /**
     * Partially update a productSpecificationCharacteristicRelationship.
     *
     * @param productSpecificationCharacteristicRelationshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationCharacteristicRelationshipDTO> partialUpdate(
        ProductSpecificationCharacteristicRelationshipDTO productSpecificationCharacteristicRelationshipDTO
    ) {
        log.debug(
            "Request to partially update ProductSpecificationCharacteristicRelationship : {}",
            productSpecificationCharacteristicRelationshipDTO
        );

        return productSpecificationCharacteristicRelationshipRepository
            .findById(productSpecificationCharacteristicRelationshipDTO.getId())
            .map(existingProductSpecificationCharacteristicRelationship -> {
                productSpecificationCharacteristicRelationshipMapper.partialUpdate(
                    existingProductSpecificationCharacteristicRelationship,
                    productSpecificationCharacteristicRelationshipDTO
                );

                return existingProductSpecificationCharacteristicRelationship;
            })
            .flatMap(productSpecificationCharacteristicRelationshipRepository::save)
            .map(productSpecificationCharacteristicRelationshipMapper::toDto);
    }

    /**
     * Get all the productSpecificationCharacteristicRelationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ProductSpecificationCharacteristicRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSpecificationCharacteristicRelationships");
        return productSpecificationCharacteristicRelationshipRepository
            .findAllBy(pageable)
            .map(productSpecificationCharacteristicRelationshipMapper::toDto);
    }

    /**
     * Returns the number of productSpecificationCharacteristicRelationships available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return productSpecificationCharacteristicRelationshipRepository.count();
    }

    /**
     * Get one productSpecificationCharacteristicRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ProductSpecificationCharacteristicRelationshipDTO> findOne(String id) {
        log.debug("Request to get ProductSpecificationCharacteristicRelationship : {}", id);
        return productSpecificationCharacteristicRelationshipRepository
            .findById(id)
            .map(productSpecificationCharacteristicRelationshipMapper::toDto);
    }

    /**
     * Delete the productSpecificationCharacteristicRelationship by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete ProductSpecificationCharacteristicRelationship : {}", id);
        return productSpecificationCharacteristicRelationshipRepository.deleteById(id);
    }
}

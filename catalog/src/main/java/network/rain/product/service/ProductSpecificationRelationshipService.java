package network.rain.product.service;

import network.rain.product.domain.ProductSpecificationRelationship;
import network.rain.product.repository.ProductSpecificationRelationshipRepository;
import network.rain.product.service.dto.ProductSpecificationRelationshipDTO;
import network.rain.product.service.mapper.ProductSpecificationRelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ProductSpecificationRelationship}.
 */
@Service
@Transactional
public class ProductSpecificationRelationshipService {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationRelationshipService.class);

    private final ProductSpecificationRelationshipRepository productSpecificationRelationshipRepository;

    private final ProductSpecificationRelationshipMapper productSpecificationRelationshipMapper;

    public ProductSpecificationRelationshipService(
        ProductSpecificationRelationshipRepository productSpecificationRelationshipRepository,
        ProductSpecificationRelationshipMapper productSpecificationRelationshipMapper
    ) {
        this.productSpecificationRelationshipRepository = productSpecificationRelationshipRepository;
        this.productSpecificationRelationshipMapper = productSpecificationRelationshipMapper;
    }

    /**
     * Save a productSpecificationRelationship.
     *
     * @param productSpecificationRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationRelationshipDTO> save(ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO) {
        log.debug("Request to save ProductSpecificationRelationship : {}", productSpecificationRelationshipDTO);
        return productSpecificationRelationshipRepository
            .save(productSpecificationRelationshipMapper.toEntity(productSpecificationRelationshipDTO))
            .map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Update a productSpecificationRelationship.
     *
     * @param productSpecificationRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationRelationshipDTO> update(ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO) {
        log.debug("Request to update ProductSpecificationRelationship : {}", productSpecificationRelationshipDTO);
        return productSpecificationRelationshipRepository
            .save(productSpecificationRelationshipMapper.toEntity(productSpecificationRelationshipDTO).setIsPersisted())
            .map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Partially update a productSpecificationRelationship.
     *
     * @param productSpecificationRelationshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationRelationshipDTO> partialUpdate(
        ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO
    ) {
        log.debug("Request to partially update ProductSpecificationRelationship : {}", productSpecificationRelationshipDTO);

        return productSpecificationRelationshipRepository
            .findById(productSpecificationRelationshipDTO.getId())
            .map(existingProductSpecificationRelationship -> {
                productSpecificationRelationshipMapper.partialUpdate(
                    existingProductSpecificationRelationship,
                    productSpecificationRelationshipDTO
                );

                return existingProductSpecificationRelationship;
            })
            .flatMap(productSpecificationRelationshipRepository::save)
            .map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Get all the productSpecificationRelationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ProductSpecificationRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSpecificationRelationships");
        return productSpecificationRelationshipRepository.findAllBy(pageable).map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Returns the number of productSpecificationRelationships available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return productSpecificationRelationshipRepository.count();
    }

    /**
     * Get one productSpecificationRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ProductSpecificationRelationshipDTO> findOne(String id) {
        log.debug("Request to get ProductSpecificationRelationship : {}", id);
        return productSpecificationRelationshipRepository.findById(id).map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Delete the productSpecificationRelationship by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete ProductSpecificationRelationship : {}", id);
        return productSpecificationRelationshipRepository.deleteById(id);
    }
}

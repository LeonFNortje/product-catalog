package network.rain.product.service;

import java.util.Optional;
import network.rain.product.domain.ProductSpecificationRelationship;
import network.rain.product.repository.ProductSpecificationRelationshipRepository;
import network.rain.product.service.dto.ProductSpecificationRelationshipDTO;
import network.rain.product.service.mapper.ProductSpecificationRelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ProductSpecificationRelationshipDTO save(ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO) {
        log.debug("Request to save ProductSpecificationRelationship : {}", productSpecificationRelationshipDTO);
        ProductSpecificationRelationship productSpecificationRelationship = productSpecificationRelationshipMapper.toEntity(
            productSpecificationRelationshipDTO
        );
        productSpecificationRelationship = productSpecificationRelationshipRepository.save(productSpecificationRelationship);
        return productSpecificationRelationshipMapper.toDto(productSpecificationRelationship);
    }

    /**
     * Update a productSpecificationRelationship.
     *
     * @param productSpecificationRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductSpecificationRelationshipDTO update(ProductSpecificationRelationshipDTO productSpecificationRelationshipDTO) {
        log.debug("Request to update ProductSpecificationRelationship : {}", productSpecificationRelationshipDTO);
        ProductSpecificationRelationship productSpecificationRelationship = productSpecificationRelationshipMapper.toEntity(
            productSpecificationRelationshipDTO
        );
        productSpecificationRelationship.setIsPersisted();
        productSpecificationRelationship = productSpecificationRelationshipRepository.save(productSpecificationRelationship);
        return productSpecificationRelationshipMapper.toDto(productSpecificationRelationship);
    }

    /**
     * Partially update a productSpecificationRelationship.
     *
     * @param productSpecificationRelationshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductSpecificationRelationshipDTO> partialUpdate(
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
            .map(productSpecificationRelationshipRepository::save)
            .map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Get all the productSpecificationRelationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductSpecificationRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSpecificationRelationships");
        return productSpecificationRelationshipRepository.findAll(pageable).map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Get one productSpecificationRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductSpecificationRelationshipDTO> findOne(String id) {
        log.debug("Request to get ProductSpecificationRelationship : {}", id);
        return productSpecificationRelationshipRepository.findById(id).map(productSpecificationRelationshipMapper::toDto);
    }

    /**
     * Delete the productSpecificationRelationship by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ProductSpecificationRelationship : {}", id);
        productSpecificationRelationshipRepository.deleteById(id);
    }
}

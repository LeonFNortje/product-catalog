package network.rain.product.service;

import network.rain.product.domain.ProductSpecificationCharacteristic;
import network.rain.product.repository.ProductSpecificationCharacteristicRepository;
import network.rain.product.service.dto.ProductSpecificationCharacteristicDTO;
import network.rain.product.service.mapper.ProductSpecificationCharacteristicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ProductSpecificationCharacteristic}.
 */
@Service
@Transactional
public class ProductSpecificationCharacteristicService {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationCharacteristicService.class);

    private final ProductSpecificationCharacteristicRepository productSpecificationCharacteristicRepository;

    private final ProductSpecificationCharacteristicMapper productSpecificationCharacteristicMapper;

    public ProductSpecificationCharacteristicService(
        ProductSpecificationCharacteristicRepository productSpecificationCharacteristicRepository,
        ProductSpecificationCharacteristicMapper productSpecificationCharacteristicMapper
    ) {
        this.productSpecificationCharacteristicRepository = productSpecificationCharacteristicRepository;
        this.productSpecificationCharacteristicMapper = productSpecificationCharacteristicMapper;
    }

    /**
     * Save a productSpecificationCharacteristic.
     *
     * @param productSpecificationCharacteristicDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationCharacteristicDTO> save(ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO) {
        log.debug("Request to save ProductSpecificationCharacteristic : {}", productSpecificationCharacteristicDTO);
        return productSpecificationCharacteristicRepository
            .save(productSpecificationCharacteristicMapper.toEntity(productSpecificationCharacteristicDTO))
            .map(productSpecificationCharacteristicMapper::toDto);
    }

    /**
     * Update a productSpecificationCharacteristic.
     *
     * @param productSpecificationCharacteristicDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationCharacteristicDTO> update(ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO) {
        log.debug("Request to update ProductSpecificationCharacteristic : {}", productSpecificationCharacteristicDTO);
        return productSpecificationCharacteristicRepository
            .save(productSpecificationCharacteristicMapper.toEntity(productSpecificationCharacteristicDTO).setIsPersisted())
            .map(productSpecificationCharacteristicMapper::toDto);
    }

    /**
     * Partially update a productSpecificationCharacteristic.
     *
     * @param productSpecificationCharacteristicDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ProductSpecificationCharacteristicDTO> partialUpdate(
        ProductSpecificationCharacteristicDTO productSpecificationCharacteristicDTO
    ) {
        log.debug("Request to partially update ProductSpecificationCharacteristic : {}", productSpecificationCharacteristicDTO);

        return productSpecificationCharacteristicRepository
            .findById(productSpecificationCharacteristicDTO.getId())
            .map(existingProductSpecificationCharacteristic -> {
                productSpecificationCharacteristicMapper.partialUpdate(
                    existingProductSpecificationCharacteristic,
                    productSpecificationCharacteristicDTO
                );

                return existingProductSpecificationCharacteristic;
            })
            .flatMap(productSpecificationCharacteristicRepository::save)
            .map(productSpecificationCharacteristicMapper::toDto);
    }

    /**
     * Get all the productSpecificationCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ProductSpecificationCharacteristicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSpecificationCharacteristics");
        return productSpecificationCharacteristicRepository.findAllBy(pageable).map(productSpecificationCharacteristicMapper::toDto);
    }

    /**
     * Returns the number of productSpecificationCharacteristics available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return productSpecificationCharacteristicRepository.count();
    }

    /**
     * Get one productSpecificationCharacteristic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ProductSpecificationCharacteristicDTO> findOne(String id) {
        log.debug("Request to get ProductSpecificationCharacteristic : {}", id);
        return productSpecificationCharacteristicRepository.findById(id).map(productSpecificationCharacteristicMapper::toDto);
    }

    /**
     * Delete the productSpecificationCharacteristic by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete ProductSpecificationCharacteristic : {}", id);
        return productSpecificationCharacteristicRepository.deleteById(id);
    }
}

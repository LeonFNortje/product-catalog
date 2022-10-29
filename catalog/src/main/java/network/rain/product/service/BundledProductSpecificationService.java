package network.rain.product.service;

import network.rain.product.domain.BundledProductSpecification;
import network.rain.product.repository.BundledProductSpecificationRepository;
import network.rain.product.service.dto.BundledProductSpecificationDTO;
import network.rain.product.service.mapper.BundledProductSpecificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link BundledProductSpecification}.
 */
@Service
@Transactional
public class BundledProductSpecificationService {

    private final Logger log = LoggerFactory.getLogger(BundledProductSpecificationService.class);

    private final BundledProductSpecificationRepository bundledProductSpecificationRepository;

    private final BundledProductSpecificationMapper bundledProductSpecificationMapper;

    public BundledProductSpecificationService(
        BundledProductSpecificationRepository bundledProductSpecificationRepository,
        BundledProductSpecificationMapper bundledProductSpecificationMapper
    ) {
        this.bundledProductSpecificationRepository = bundledProductSpecificationRepository;
        this.bundledProductSpecificationMapper = bundledProductSpecificationMapper;
    }

    /**
     * Save a bundledProductSpecification.
     *
     * @param bundledProductSpecificationDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<BundledProductSpecificationDTO> save(BundledProductSpecificationDTO bundledProductSpecificationDTO) {
        log.debug("Request to save BundledProductSpecification : {}", bundledProductSpecificationDTO);
        return bundledProductSpecificationRepository
            .save(bundledProductSpecificationMapper.toEntity(bundledProductSpecificationDTO))
            .map(bundledProductSpecificationMapper::toDto);
    }

    /**
     * Update a bundledProductSpecification.
     *
     * @param bundledProductSpecificationDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<BundledProductSpecificationDTO> update(BundledProductSpecificationDTO bundledProductSpecificationDTO) {
        log.debug("Request to update BundledProductSpecification : {}", bundledProductSpecificationDTO);
        return bundledProductSpecificationRepository
            .save(bundledProductSpecificationMapper.toEntity(bundledProductSpecificationDTO).setIsPersisted())
            .map(bundledProductSpecificationMapper::toDto);
    }

    /**
     * Partially update a bundledProductSpecification.
     *
     * @param bundledProductSpecificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<BundledProductSpecificationDTO> partialUpdate(BundledProductSpecificationDTO bundledProductSpecificationDTO) {
        log.debug("Request to partially update BundledProductSpecification : {}", bundledProductSpecificationDTO);

        return bundledProductSpecificationRepository
            .findById(bundledProductSpecificationDTO.getId())
            .map(existingBundledProductSpecification -> {
                bundledProductSpecificationMapper.partialUpdate(existingBundledProductSpecification, bundledProductSpecificationDTO);

                return existingBundledProductSpecification;
            })
            .flatMap(bundledProductSpecificationRepository::save)
            .map(bundledProductSpecificationMapper::toDto);
    }

    /**
     * Get all the bundledProductSpecifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<BundledProductSpecificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BundledProductSpecifications");
        return bundledProductSpecificationRepository.findAllBy(pageable).map(bundledProductSpecificationMapper::toDto);
    }

    /**
     * Returns the number of bundledProductSpecifications available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return bundledProductSpecificationRepository.count();
    }

    /**
     * Get one bundledProductSpecification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<BundledProductSpecificationDTO> findOne(String id) {
        log.debug("Request to get BundledProductSpecification : {}", id);
        return bundledProductSpecificationRepository.findById(id).map(bundledProductSpecificationMapper::toDto);
    }

    /**
     * Delete the bundledProductSpecification by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete BundledProductSpecification : {}", id);
        return bundledProductSpecificationRepository.deleteById(id);
    }
}

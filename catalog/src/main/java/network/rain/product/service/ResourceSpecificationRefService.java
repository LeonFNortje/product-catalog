package network.rain.product.service;

import network.rain.product.domain.ResourceSpecificationRef;
import network.rain.product.repository.ResourceSpecificationRefRepository;
import network.rain.product.service.dto.ResourceSpecificationRefDTO;
import network.rain.product.service.mapper.ResourceSpecificationRefMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ResourceSpecificationRef}.
 */
@Service
@Transactional
public class ResourceSpecificationRefService {

    private final Logger log = LoggerFactory.getLogger(ResourceSpecificationRefService.class);

    private final ResourceSpecificationRefRepository resourceSpecificationRefRepository;

    private final ResourceSpecificationRefMapper resourceSpecificationRefMapper;

    public ResourceSpecificationRefService(
        ResourceSpecificationRefRepository resourceSpecificationRefRepository,
        ResourceSpecificationRefMapper resourceSpecificationRefMapper
    ) {
        this.resourceSpecificationRefRepository = resourceSpecificationRefRepository;
        this.resourceSpecificationRefMapper = resourceSpecificationRefMapper;
    }

    /**
     * Save a resourceSpecificationRef.
     *
     * @param resourceSpecificationRefDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ResourceSpecificationRefDTO> save(ResourceSpecificationRefDTO resourceSpecificationRefDTO) {
        log.debug("Request to save ResourceSpecificationRef : {}", resourceSpecificationRefDTO);
        return resourceSpecificationRefRepository
            .save(resourceSpecificationRefMapper.toEntity(resourceSpecificationRefDTO))
            .map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Update a resourceSpecificationRef.
     *
     * @param resourceSpecificationRefDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ResourceSpecificationRefDTO> update(ResourceSpecificationRefDTO resourceSpecificationRefDTO) {
        log.debug("Request to update ResourceSpecificationRef : {}", resourceSpecificationRefDTO);
        return resourceSpecificationRefRepository
            .save(resourceSpecificationRefMapper.toEntity(resourceSpecificationRefDTO).setIsPersisted())
            .map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Partially update a resourceSpecificationRef.
     *
     * @param resourceSpecificationRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ResourceSpecificationRefDTO> partialUpdate(ResourceSpecificationRefDTO resourceSpecificationRefDTO) {
        log.debug("Request to partially update ResourceSpecificationRef : {}", resourceSpecificationRefDTO);

        return resourceSpecificationRefRepository
            .findById(resourceSpecificationRefDTO.getId())
            .map(existingResourceSpecificationRef -> {
                resourceSpecificationRefMapper.partialUpdate(existingResourceSpecificationRef, resourceSpecificationRefDTO);

                return existingResourceSpecificationRef;
            })
            .flatMap(resourceSpecificationRefRepository::save)
            .map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Get all the resourceSpecificationRefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ResourceSpecificationRefDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceSpecificationRefs");
        return resourceSpecificationRefRepository.findAllBy(pageable).map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Returns the number of resourceSpecificationRefs available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return resourceSpecificationRefRepository.count();
    }

    /**
     * Get one resourceSpecificationRef by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ResourceSpecificationRefDTO> findOne(String id) {
        log.debug("Request to get ResourceSpecificationRef : {}", id);
        return resourceSpecificationRefRepository.findById(id).map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Delete the resourceSpecificationRef by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete ResourceSpecificationRef : {}", id);
        return resourceSpecificationRefRepository.deleteById(id);
    }
}

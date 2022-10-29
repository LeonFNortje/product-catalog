package network.rain.product.service;

import java.util.Optional;
import network.rain.product.domain.ResourceSpecificationRef;
import network.rain.product.repository.ResourceSpecificationRefRepository;
import network.rain.product.service.dto.ResourceSpecificationRefDTO;
import network.rain.product.service.mapper.ResourceSpecificationRefMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ResourceSpecificationRefDTO save(ResourceSpecificationRefDTO resourceSpecificationRefDTO) {
        log.debug("Request to save ResourceSpecificationRef : {}", resourceSpecificationRefDTO);
        ResourceSpecificationRef resourceSpecificationRef = resourceSpecificationRefMapper.toEntity(resourceSpecificationRefDTO);
        resourceSpecificationRef = resourceSpecificationRefRepository.save(resourceSpecificationRef);
        return resourceSpecificationRefMapper.toDto(resourceSpecificationRef);
    }

    /**
     * Update a resourceSpecificationRef.
     *
     * @param resourceSpecificationRefDTO the entity to save.
     * @return the persisted entity.
     */
    public ResourceSpecificationRefDTO update(ResourceSpecificationRefDTO resourceSpecificationRefDTO) {
        log.debug("Request to update ResourceSpecificationRef : {}", resourceSpecificationRefDTO);
        ResourceSpecificationRef resourceSpecificationRef = resourceSpecificationRefMapper.toEntity(resourceSpecificationRefDTO);
        resourceSpecificationRef.setIsPersisted();
        resourceSpecificationRef = resourceSpecificationRefRepository.save(resourceSpecificationRef);
        return resourceSpecificationRefMapper.toDto(resourceSpecificationRef);
    }

    /**
     * Partially update a resourceSpecificationRef.
     *
     * @param resourceSpecificationRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ResourceSpecificationRefDTO> partialUpdate(ResourceSpecificationRefDTO resourceSpecificationRefDTO) {
        log.debug("Request to partially update ResourceSpecificationRef : {}", resourceSpecificationRefDTO);

        return resourceSpecificationRefRepository
            .findById(resourceSpecificationRefDTO.getId())
            .map(existingResourceSpecificationRef -> {
                resourceSpecificationRefMapper.partialUpdate(existingResourceSpecificationRef, resourceSpecificationRefDTO);

                return existingResourceSpecificationRef;
            })
            .map(resourceSpecificationRefRepository::save)
            .map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Get all the resourceSpecificationRefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourceSpecificationRefDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceSpecificationRefs");
        return resourceSpecificationRefRepository.findAll(pageable).map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Get one resourceSpecificationRef by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResourceSpecificationRefDTO> findOne(String id) {
        log.debug("Request to get ResourceSpecificationRef : {}", id);
        return resourceSpecificationRefRepository.findById(id).map(resourceSpecificationRefMapper::toDto);
    }

    /**
     * Delete the resourceSpecificationRef by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ResourceSpecificationRef : {}", id);
        resourceSpecificationRefRepository.deleteById(id);
    }
}

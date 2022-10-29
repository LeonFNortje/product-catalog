package network.rain.product.service;

import network.rain.product.domain.TargetProductSchema;
import network.rain.product.repository.TargetProductSchemaRepository;
import network.rain.product.service.dto.TargetProductSchemaDTO;
import network.rain.product.service.mapper.TargetProductSchemaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link TargetProductSchema}.
 */
@Service
@Transactional
public class TargetProductSchemaService {

    private final Logger log = LoggerFactory.getLogger(TargetProductSchemaService.class);

    private final TargetProductSchemaRepository targetProductSchemaRepository;

    private final TargetProductSchemaMapper targetProductSchemaMapper;

    public TargetProductSchemaService(
        TargetProductSchemaRepository targetProductSchemaRepository,
        TargetProductSchemaMapper targetProductSchemaMapper
    ) {
        this.targetProductSchemaRepository = targetProductSchemaRepository;
        this.targetProductSchemaMapper = targetProductSchemaMapper;
    }

    /**
     * Save a targetProductSchema.
     *
     * @param targetProductSchemaDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<TargetProductSchemaDTO> save(TargetProductSchemaDTO targetProductSchemaDTO) {
        log.debug("Request to save TargetProductSchema : {}", targetProductSchemaDTO);
        return targetProductSchemaRepository
            .save(targetProductSchemaMapper.toEntity(targetProductSchemaDTO))
            .map(targetProductSchemaMapper::toDto);
    }

    /**
     * Update a targetProductSchema.
     *
     * @param targetProductSchemaDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<TargetProductSchemaDTO> update(TargetProductSchemaDTO targetProductSchemaDTO) {
        log.debug("Request to update TargetProductSchema : {}", targetProductSchemaDTO);
        return targetProductSchemaRepository
            .save(targetProductSchemaMapper.toEntity(targetProductSchemaDTO))
            .map(targetProductSchemaMapper::toDto);
    }

    /**
     * Partially update a targetProductSchema.
     *
     * @param targetProductSchemaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<TargetProductSchemaDTO> partialUpdate(TargetProductSchemaDTO targetProductSchemaDTO) {
        log.debug("Request to partially update TargetProductSchema : {}", targetProductSchemaDTO);

        return targetProductSchemaRepository
            .findById(targetProductSchemaDTO.getId())
            .map(existingTargetProductSchema -> {
                targetProductSchemaMapper.partialUpdate(existingTargetProductSchema, targetProductSchemaDTO);

                return existingTargetProductSchema;
            })
            .flatMap(targetProductSchemaRepository::save)
            .map(targetProductSchemaMapper::toDto);
    }

    /**
     * Get all the targetProductSchemas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<TargetProductSchemaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TargetProductSchemas");
        return targetProductSchemaRepository.findAllBy(pageable).map(targetProductSchemaMapper::toDto);
    }

    /**
     * Returns the number of targetProductSchemas available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return targetProductSchemaRepository.count();
    }

    /**
     * Get one targetProductSchema by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<TargetProductSchemaDTO> findOne(Long id) {
        log.debug("Request to get TargetProductSchema : {}", id);
        return targetProductSchemaRepository.findById(id).map(targetProductSchemaMapper::toDto);
    }

    /**
     * Delete the targetProductSchema by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete TargetProductSchema : {}", id);
        return targetProductSchemaRepository.deleteById(id);
    }
}

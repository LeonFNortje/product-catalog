package network.rain.product.service;

import java.util.Optional;
import network.rain.product.domain.TargetProductSchema;
import network.rain.product.repository.TargetProductSchemaRepository;
import network.rain.product.service.dto.TargetProductSchemaDTO;
import network.rain.product.service.mapper.TargetProductSchemaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TargetProductSchemaDTO save(TargetProductSchemaDTO targetProductSchemaDTO) {
        log.debug("Request to save TargetProductSchema : {}", targetProductSchemaDTO);
        TargetProductSchema targetProductSchema = targetProductSchemaMapper.toEntity(targetProductSchemaDTO);
        targetProductSchema = targetProductSchemaRepository.save(targetProductSchema);
        return targetProductSchemaMapper.toDto(targetProductSchema);
    }

    /**
     * Update a targetProductSchema.
     *
     * @param targetProductSchemaDTO the entity to save.
     * @return the persisted entity.
     */
    public TargetProductSchemaDTO update(TargetProductSchemaDTO targetProductSchemaDTO) {
        log.debug("Request to update TargetProductSchema : {}", targetProductSchemaDTO);
        TargetProductSchema targetProductSchema = targetProductSchemaMapper.toEntity(targetProductSchemaDTO);
        targetProductSchema = targetProductSchemaRepository.save(targetProductSchema);
        return targetProductSchemaMapper.toDto(targetProductSchema);
    }

    /**
     * Partially update a targetProductSchema.
     *
     * @param targetProductSchemaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TargetProductSchemaDTO> partialUpdate(TargetProductSchemaDTO targetProductSchemaDTO) {
        log.debug("Request to partially update TargetProductSchema : {}", targetProductSchemaDTO);

        return targetProductSchemaRepository
            .findById(targetProductSchemaDTO.getId())
            .map(existingTargetProductSchema -> {
                targetProductSchemaMapper.partialUpdate(existingTargetProductSchema, targetProductSchemaDTO);

                return existingTargetProductSchema;
            })
            .map(targetProductSchemaRepository::save)
            .map(targetProductSchemaMapper::toDto);
    }

    /**
     * Get all the targetProductSchemas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TargetProductSchemaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TargetProductSchemas");
        return targetProductSchemaRepository.findAll(pageable).map(targetProductSchemaMapper::toDto);
    }

    /**
     * Get one targetProductSchema by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TargetProductSchemaDTO> findOne(Long id) {
        log.debug("Request to get TargetProductSchema : {}", id);
        return targetProductSchemaRepository.findById(id).map(targetProductSchemaMapper::toDto);
    }

    /**
     * Delete the targetProductSchema by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TargetProductSchema : {}", id);
        targetProductSchemaRepository.deleteById(id);
    }
}

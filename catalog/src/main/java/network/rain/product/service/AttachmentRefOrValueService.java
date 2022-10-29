package network.rain.product.service;

import network.rain.product.domain.AttachmentRefOrValue;
import network.rain.product.repository.AttachmentRefOrValueRepository;
import network.rain.product.service.dto.AttachmentRefOrValueDTO;
import network.rain.product.service.mapper.AttachmentRefOrValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link AttachmentRefOrValue}.
 */
@Service
@Transactional
public class AttachmentRefOrValueService {

    private final Logger log = LoggerFactory.getLogger(AttachmentRefOrValueService.class);

    private final AttachmentRefOrValueRepository attachmentRefOrValueRepository;

    private final AttachmentRefOrValueMapper attachmentRefOrValueMapper;

    public AttachmentRefOrValueService(
        AttachmentRefOrValueRepository attachmentRefOrValueRepository,
        AttachmentRefOrValueMapper attachmentRefOrValueMapper
    ) {
        this.attachmentRefOrValueRepository = attachmentRefOrValueRepository;
        this.attachmentRefOrValueMapper = attachmentRefOrValueMapper;
    }

    /**
     * Save a attachmentRefOrValue.
     *
     * @param attachmentRefOrValueDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AttachmentRefOrValueDTO> save(AttachmentRefOrValueDTO attachmentRefOrValueDTO) {
        log.debug("Request to save AttachmentRefOrValue : {}", attachmentRefOrValueDTO);
        return attachmentRefOrValueRepository
            .save(attachmentRefOrValueMapper.toEntity(attachmentRefOrValueDTO))
            .map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Update a attachmentRefOrValue.
     *
     * @param attachmentRefOrValueDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AttachmentRefOrValueDTO> update(AttachmentRefOrValueDTO attachmentRefOrValueDTO) {
        log.debug("Request to update AttachmentRefOrValue : {}", attachmentRefOrValueDTO);
        return attachmentRefOrValueRepository
            .save(attachmentRefOrValueMapper.toEntity(attachmentRefOrValueDTO).setIsPersisted())
            .map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Partially update a attachmentRefOrValue.
     *
     * @param attachmentRefOrValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<AttachmentRefOrValueDTO> partialUpdate(AttachmentRefOrValueDTO attachmentRefOrValueDTO) {
        log.debug("Request to partially update AttachmentRefOrValue : {}", attachmentRefOrValueDTO);

        return attachmentRefOrValueRepository
            .findById(attachmentRefOrValueDTO.getId())
            .map(existingAttachmentRefOrValue -> {
                attachmentRefOrValueMapper.partialUpdate(existingAttachmentRefOrValue, attachmentRefOrValueDTO);

                return existingAttachmentRefOrValue;
            })
            .flatMap(attachmentRefOrValueRepository::save)
            .map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Get all the attachmentRefOrValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<AttachmentRefOrValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentRefOrValues");
        return attachmentRefOrValueRepository.findAllBy(pageable).map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Returns the number of attachmentRefOrValues available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return attachmentRefOrValueRepository.count();
    }

    /**
     * Get one attachmentRefOrValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<AttachmentRefOrValueDTO> findOne(String id) {
        log.debug("Request to get AttachmentRefOrValue : {}", id);
        return attachmentRefOrValueRepository.findById(id).map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Delete the attachmentRefOrValue by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete AttachmentRefOrValue : {}", id);
        return attachmentRefOrValueRepository.deleteById(id);
    }
}

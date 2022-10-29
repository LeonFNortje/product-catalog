package network.rain.product.service;

import java.util.Optional;
import network.rain.product.domain.AttachmentRefOrValue;
import network.rain.product.repository.AttachmentRefOrValueRepository;
import network.rain.product.service.dto.AttachmentRefOrValueDTO;
import network.rain.product.service.mapper.AttachmentRefOrValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AttachmentRefOrValueDTO save(AttachmentRefOrValueDTO attachmentRefOrValueDTO) {
        log.debug("Request to save AttachmentRefOrValue : {}", attachmentRefOrValueDTO);
        AttachmentRefOrValue attachmentRefOrValue = attachmentRefOrValueMapper.toEntity(attachmentRefOrValueDTO);
        attachmentRefOrValue = attachmentRefOrValueRepository.save(attachmentRefOrValue);
        return attachmentRefOrValueMapper.toDto(attachmentRefOrValue);
    }

    /**
     * Update a attachmentRefOrValue.
     *
     * @param attachmentRefOrValueDTO the entity to save.
     * @return the persisted entity.
     */
    public AttachmentRefOrValueDTO update(AttachmentRefOrValueDTO attachmentRefOrValueDTO) {
        log.debug("Request to update AttachmentRefOrValue : {}", attachmentRefOrValueDTO);
        AttachmentRefOrValue attachmentRefOrValue = attachmentRefOrValueMapper.toEntity(attachmentRefOrValueDTO);
        attachmentRefOrValue.setIsPersisted();
        attachmentRefOrValue = attachmentRefOrValueRepository.save(attachmentRefOrValue);
        return attachmentRefOrValueMapper.toDto(attachmentRefOrValue);
    }

    /**
     * Partially update a attachmentRefOrValue.
     *
     * @param attachmentRefOrValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AttachmentRefOrValueDTO> partialUpdate(AttachmentRefOrValueDTO attachmentRefOrValueDTO) {
        log.debug("Request to partially update AttachmentRefOrValue : {}", attachmentRefOrValueDTO);

        return attachmentRefOrValueRepository
            .findById(attachmentRefOrValueDTO.getId())
            .map(existingAttachmentRefOrValue -> {
                attachmentRefOrValueMapper.partialUpdate(existingAttachmentRefOrValue, attachmentRefOrValueDTO);

                return existingAttachmentRefOrValue;
            })
            .map(attachmentRefOrValueRepository::save)
            .map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Get all the attachmentRefOrValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttachmentRefOrValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttachmentRefOrValues");
        return attachmentRefOrValueRepository.findAll(pageable).map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Get one attachmentRefOrValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttachmentRefOrValueDTO> findOne(String id) {
        log.debug("Request to get AttachmentRefOrValue : {}", id);
        return attachmentRefOrValueRepository.findById(id).map(attachmentRefOrValueMapper::toDto);
    }

    /**
     * Delete the attachmentRefOrValue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete AttachmentRefOrValue : {}", id);
        attachmentRefOrValueRepository.deleteById(id);
    }
}

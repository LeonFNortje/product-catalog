package network.rain.account.service;

import java.util.Optional;
import network.rain.account.domain.RelatedParty;
import network.rain.account.repository.RelatedPartyRepository;
import network.rain.account.service.dto.RelatedPartyDTO;
import network.rain.account.service.mapper.RelatedPartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RelatedParty}.
 */
@Service
@Transactional
public class RelatedPartyService {

    private final Logger log = LoggerFactory.getLogger(RelatedPartyService.class);

    private final RelatedPartyRepository relatedPartyRepository;

    private final RelatedPartyMapper relatedPartyMapper;

    public RelatedPartyService(RelatedPartyRepository relatedPartyRepository, RelatedPartyMapper relatedPartyMapper) {
        this.relatedPartyRepository = relatedPartyRepository;
        this.relatedPartyMapper = relatedPartyMapper;
    }

    /**
     * Save a relatedParty.
     *
     * @param relatedPartyDTO the entity to save.
     * @return the persisted entity.
     */
    public RelatedPartyDTO save(RelatedPartyDTO relatedPartyDTO) {
        log.debug("Request to save RelatedParty : {}", relatedPartyDTO);
        RelatedParty relatedParty = relatedPartyMapper.toEntity(relatedPartyDTO);
        relatedParty = relatedPartyRepository.save(relatedParty);
        return relatedPartyMapper.toDto(relatedParty);
    }

    /**
     * Update a relatedParty.
     *
     * @param relatedPartyDTO the entity to save.
     * @return the persisted entity.
     */
    public RelatedPartyDTO update(RelatedPartyDTO relatedPartyDTO) {
        log.debug("Request to update RelatedParty : {}", relatedPartyDTO);
        RelatedParty relatedParty = relatedPartyMapper.toEntity(relatedPartyDTO);
        relatedParty.setIsPersisted();
        relatedParty = relatedPartyRepository.save(relatedParty);
        return relatedPartyMapper.toDto(relatedParty);
    }

    /**
     * Partially update a relatedParty.
     *
     * @param relatedPartyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RelatedPartyDTO> partialUpdate(RelatedPartyDTO relatedPartyDTO) {
        log.debug("Request to partially update RelatedParty : {}", relatedPartyDTO);

        return relatedPartyRepository
            .findById(relatedPartyDTO.getId())
            .map(existingRelatedParty -> {
                relatedPartyMapper.partialUpdate(existingRelatedParty, relatedPartyDTO);

                return existingRelatedParty;
            })
            .map(relatedPartyRepository::save)
            .map(relatedPartyMapper::toDto);
    }

    /**
     * Get all the relatedParties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RelatedPartyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelatedParties");
        return relatedPartyRepository.findAll(pageable).map(relatedPartyMapper::toDto);
    }

    /**
     * Get one relatedParty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RelatedPartyDTO> findOne(String id) {
        log.debug("Request to get RelatedParty : {}", id);
        return relatedPartyRepository.findById(id).map(relatedPartyMapper::toDto);
    }

    /**
     * Delete the relatedParty by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RelatedParty : {}", id);
        relatedPartyRepository.deleteById(id);
    }
}

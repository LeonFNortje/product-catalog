package network.rain.product.service;

import network.rain.product.domain.RelatedParty;
import network.rain.product.repository.RelatedPartyRepository;
import network.rain.product.service.dto.RelatedPartyDTO;
import network.rain.product.service.mapper.RelatedPartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<RelatedPartyDTO> save(RelatedPartyDTO relatedPartyDTO) {
        log.debug("Request to save RelatedParty : {}", relatedPartyDTO);
        return relatedPartyRepository.save(relatedPartyMapper.toEntity(relatedPartyDTO)).map(relatedPartyMapper::toDto);
    }

    /**
     * Update a relatedParty.
     *
     * @param relatedPartyDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<RelatedPartyDTO> update(RelatedPartyDTO relatedPartyDTO) {
        log.debug("Request to update RelatedParty : {}", relatedPartyDTO);
        return relatedPartyRepository.save(relatedPartyMapper.toEntity(relatedPartyDTO).setIsPersisted()).map(relatedPartyMapper::toDto);
    }

    /**
     * Partially update a relatedParty.
     *
     * @param relatedPartyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<RelatedPartyDTO> partialUpdate(RelatedPartyDTO relatedPartyDTO) {
        log.debug("Request to partially update RelatedParty : {}", relatedPartyDTO);

        return relatedPartyRepository
            .findById(relatedPartyDTO.getId())
            .map(existingRelatedParty -> {
                relatedPartyMapper.partialUpdate(existingRelatedParty, relatedPartyDTO);

                return existingRelatedParty;
            })
            .flatMap(relatedPartyRepository::save)
            .map(relatedPartyMapper::toDto);
    }

    /**
     * Get all the relatedParties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<RelatedPartyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelatedParties");
        return relatedPartyRepository.findAllBy(pageable).map(relatedPartyMapper::toDto);
    }

    /**
     * Returns the number of relatedParties available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return relatedPartyRepository.count();
    }

    /**
     * Get one relatedParty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<RelatedPartyDTO> findOne(String id) {
        log.debug("Request to get RelatedParty : {}", id);
        return relatedPartyRepository.findById(id).map(relatedPartyMapper::toDto);
    }

    /**
     * Delete the relatedParty by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete RelatedParty : {}", id);
        return relatedPartyRepository.deleteById(id);
    }
}
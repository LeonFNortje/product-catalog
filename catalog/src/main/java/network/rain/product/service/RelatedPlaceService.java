package network.rain.product.service;

import network.rain.product.domain.RelatedPlace;
import network.rain.product.repository.RelatedPlaceRepository;
import network.rain.product.service.dto.RelatedPlaceDTO;
import network.rain.product.service.mapper.RelatedPlaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link RelatedPlace}.
 */
@Service
@Transactional
public class RelatedPlaceService {

    private final Logger log = LoggerFactory.getLogger(RelatedPlaceService.class);

    private final RelatedPlaceRepository relatedPlaceRepository;

    private final RelatedPlaceMapper relatedPlaceMapper;

    public RelatedPlaceService(RelatedPlaceRepository relatedPlaceRepository, RelatedPlaceMapper relatedPlaceMapper) {
        this.relatedPlaceRepository = relatedPlaceRepository;
        this.relatedPlaceMapper = relatedPlaceMapper;
    }

    /**
     * Save a relatedPlace.
     *
     * @param relatedPlaceDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<RelatedPlaceDTO> save(RelatedPlaceDTO relatedPlaceDTO) {
        log.debug("Request to save RelatedPlace : {}", relatedPlaceDTO);
        return relatedPlaceRepository.save(relatedPlaceMapper.toEntity(relatedPlaceDTO)).map(relatedPlaceMapper::toDto);
    }

    /**
     * Update a relatedPlace.
     *
     * @param relatedPlaceDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<RelatedPlaceDTO> update(RelatedPlaceDTO relatedPlaceDTO) {
        log.debug("Request to update RelatedPlace : {}", relatedPlaceDTO);
        return relatedPlaceRepository.save(relatedPlaceMapper.toEntity(relatedPlaceDTO).setIsPersisted()).map(relatedPlaceMapper::toDto);
    }

    /**
     * Partially update a relatedPlace.
     *
     * @param relatedPlaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<RelatedPlaceDTO> partialUpdate(RelatedPlaceDTO relatedPlaceDTO) {
        log.debug("Request to partially update RelatedPlace : {}", relatedPlaceDTO);

        return relatedPlaceRepository
            .findById(relatedPlaceDTO.getId())
            .map(existingRelatedPlace -> {
                relatedPlaceMapper.partialUpdate(existingRelatedPlace, relatedPlaceDTO);

                return existingRelatedPlace;
            })
            .flatMap(relatedPlaceRepository::save)
            .map(relatedPlaceMapper::toDto);
    }

    /**
     * Get all the relatedPlaces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<RelatedPlaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelatedPlaces");
        return relatedPlaceRepository.findAllBy(pageable).map(relatedPlaceMapper::toDto);
    }

    /**
     * Returns the number of relatedPlaces available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return relatedPlaceRepository.count();
    }

    /**
     * Get one relatedPlace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<RelatedPlaceDTO> findOne(String id) {
        log.debug("Request to get RelatedPlace : {}", id);
        return relatedPlaceRepository.findById(id).map(relatedPlaceMapper::toDto);
    }

    /**
     * Delete the relatedPlace by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete RelatedPlace : {}", id);
        return relatedPlaceRepository.deleteById(id);
    }
}

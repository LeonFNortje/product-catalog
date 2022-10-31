package network.rain.account.service;

import java.util.Optional;
import network.rain.account.domain.RelatedPlace;
import network.rain.account.repository.RelatedPlaceRepository;
import network.rain.account.service.dto.RelatedPlaceDTO;
import network.rain.account.service.mapper.RelatedPlaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public RelatedPlaceDTO save(RelatedPlaceDTO relatedPlaceDTO) {
        log.debug("Request to save RelatedPlace : {}", relatedPlaceDTO);
        RelatedPlace relatedPlace = relatedPlaceMapper.toEntity(relatedPlaceDTO);
        relatedPlace = relatedPlaceRepository.save(relatedPlace);
        return relatedPlaceMapper.toDto(relatedPlace);
    }

    /**
     * Update a relatedPlace.
     *
     * @param relatedPlaceDTO the entity to save.
     * @return the persisted entity.
     */
    public RelatedPlaceDTO update(RelatedPlaceDTO relatedPlaceDTO) {
        log.debug("Request to update RelatedPlace : {}", relatedPlaceDTO);
        RelatedPlace relatedPlace = relatedPlaceMapper.toEntity(relatedPlaceDTO);
        relatedPlace.setIsPersisted();
        relatedPlace = relatedPlaceRepository.save(relatedPlace);
        return relatedPlaceMapper.toDto(relatedPlace);
    }

    /**
     * Partially update a relatedPlace.
     *
     * @param relatedPlaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RelatedPlaceDTO> partialUpdate(RelatedPlaceDTO relatedPlaceDTO) {
        log.debug("Request to partially update RelatedPlace : {}", relatedPlaceDTO);

        return relatedPlaceRepository
            .findById(relatedPlaceDTO.getId())
            .map(existingRelatedPlace -> {
                relatedPlaceMapper.partialUpdate(existingRelatedPlace, relatedPlaceDTO);

                return existingRelatedPlace;
            })
            .map(relatedPlaceRepository::save)
            .map(relatedPlaceMapper::toDto);
    }

    /**
     * Get all the relatedPlaces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RelatedPlaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelatedPlaces");
        return relatedPlaceRepository.findAll(pageable).map(relatedPlaceMapper::toDto);
    }

    /**
     * Get one relatedPlace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RelatedPlaceDTO> findOne(String id) {
        log.debug("Request to get RelatedPlace : {}", id);
        return relatedPlaceRepository.findById(id).map(relatedPlaceMapper::toDto);
    }

    /**
     * Delete the relatedPlace by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete RelatedPlace : {}", id);
        relatedPlaceRepository.deleteById(id);
    }
}

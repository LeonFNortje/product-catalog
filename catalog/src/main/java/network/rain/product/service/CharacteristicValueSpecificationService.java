package network.rain.product.service;

import network.rain.product.domain.CharacteristicValueSpecification;
import network.rain.product.repository.CharacteristicValueSpecificationRepository;
import network.rain.product.service.dto.CharacteristicValueSpecificationDTO;
import network.rain.product.service.mapper.CharacteristicValueSpecificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link CharacteristicValueSpecification}.
 */
@Service
@Transactional
public class CharacteristicValueSpecificationService {

    private final Logger log = LoggerFactory.getLogger(CharacteristicValueSpecificationService.class);

    private final CharacteristicValueSpecificationRepository characteristicValueSpecificationRepository;

    private final CharacteristicValueSpecificationMapper characteristicValueSpecificationMapper;

    public CharacteristicValueSpecificationService(
        CharacteristicValueSpecificationRepository characteristicValueSpecificationRepository,
        CharacteristicValueSpecificationMapper characteristicValueSpecificationMapper
    ) {
        this.characteristicValueSpecificationRepository = characteristicValueSpecificationRepository;
        this.characteristicValueSpecificationMapper = characteristicValueSpecificationMapper;
    }

    /**
     * Save a characteristicValueSpecification.
     *
     * @param characteristicValueSpecificationDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CharacteristicValueSpecificationDTO> save(CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO) {
        log.debug("Request to save CharacteristicValueSpecification : {}", characteristicValueSpecificationDTO);
        return characteristicValueSpecificationRepository
            .save(characteristicValueSpecificationMapper.toEntity(characteristicValueSpecificationDTO))
            .map(characteristicValueSpecificationMapper::toDto);
    }

    /**
     * Update a characteristicValueSpecification.
     *
     * @param characteristicValueSpecificationDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CharacteristicValueSpecificationDTO> update(CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO) {
        log.debug("Request to update CharacteristicValueSpecification : {}", characteristicValueSpecificationDTO);
        return characteristicValueSpecificationRepository
            .save(characteristicValueSpecificationMapper.toEntity(characteristicValueSpecificationDTO))
            .map(characteristicValueSpecificationMapper::toDto);
    }

    /**
     * Partially update a characteristicValueSpecification.
     *
     * @param characteristicValueSpecificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<CharacteristicValueSpecificationDTO> partialUpdate(
        CharacteristicValueSpecificationDTO characteristicValueSpecificationDTO
    ) {
        log.debug("Request to partially update CharacteristicValueSpecification : {}", characteristicValueSpecificationDTO);

        return characteristicValueSpecificationRepository
            .findById(characteristicValueSpecificationDTO.getId())
            .map(existingCharacteristicValueSpecification -> {
                characteristicValueSpecificationMapper.partialUpdate(
                    existingCharacteristicValueSpecification,
                    characteristicValueSpecificationDTO
                );

                return existingCharacteristicValueSpecification;
            })
            .flatMap(characteristicValueSpecificationRepository::save)
            .map(characteristicValueSpecificationMapper::toDto);
    }

    /**
     * Get all the characteristicValueSpecifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<CharacteristicValueSpecificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CharacteristicValueSpecifications");
        return characteristicValueSpecificationRepository.findAllBy(pageable).map(characteristicValueSpecificationMapper::toDto);
    }

    /**
     * Returns the number of characteristicValueSpecifications available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return characteristicValueSpecificationRepository.count();
    }

    /**
     * Get one characteristicValueSpecification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<CharacteristicValueSpecificationDTO> findOne(Long id) {
        log.debug("Request to get CharacteristicValueSpecification : {}", id);
        return characteristicValueSpecificationRepository.findById(id).map(characteristicValueSpecificationMapper::toDto);
    }

    /**
     * Delete the characteristicValueSpecification by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete CharacteristicValueSpecification : {}", id);
        return characteristicValueSpecificationRepository.deleteById(id);
    }
}

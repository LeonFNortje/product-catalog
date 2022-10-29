package network.rain.product.service;

import java.util.Optional;
import network.rain.product.domain.ServiceSpecificationRef;
import network.rain.product.repository.ServiceSpecificationRefRepository;
import network.rain.product.service.dto.ServiceSpecificationRefDTO;
import network.rain.product.service.mapper.ServiceSpecificationRefMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServiceSpecificationRef}.
 */
@Service
@Transactional
public class ServiceSpecificationRefService {

    private final Logger log = LoggerFactory.getLogger(ServiceSpecificationRefService.class);

    private final ServiceSpecificationRefRepository serviceSpecificationRefRepository;

    private final ServiceSpecificationRefMapper serviceSpecificationRefMapper;

    public ServiceSpecificationRefService(
        ServiceSpecificationRefRepository serviceSpecificationRefRepository,
        ServiceSpecificationRefMapper serviceSpecificationRefMapper
    ) {
        this.serviceSpecificationRefRepository = serviceSpecificationRefRepository;
        this.serviceSpecificationRefMapper = serviceSpecificationRefMapper;
    }

    /**
     * Save a serviceSpecificationRef.
     *
     * @param serviceSpecificationRefDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceSpecificationRefDTO save(ServiceSpecificationRefDTO serviceSpecificationRefDTO) {
        log.debug("Request to save ServiceSpecificationRef : {}", serviceSpecificationRefDTO);
        ServiceSpecificationRef serviceSpecificationRef = serviceSpecificationRefMapper.toEntity(serviceSpecificationRefDTO);
        serviceSpecificationRef = serviceSpecificationRefRepository.save(serviceSpecificationRef);
        return serviceSpecificationRefMapper.toDto(serviceSpecificationRef);
    }

    /**
     * Update a serviceSpecificationRef.
     *
     * @param serviceSpecificationRefDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceSpecificationRefDTO update(ServiceSpecificationRefDTO serviceSpecificationRefDTO) {
        log.debug("Request to update ServiceSpecificationRef : {}", serviceSpecificationRefDTO);
        ServiceSpecificationRef serviceSpecificationRef = serviceSpecificationRefMapper.toEntity(serviceSpecificationRefDTO);
        serviceSpecificationRef.setIsPersisted();
        serviceSpecificationRef = serviceSpecificationRefRepository.save(serviceSpecificationRef);
        return serviceSpecificationRefMapper.toDto(serviceSpecificationRef);
    }

    /**
     * Partially update a serviceSpecificationRef.
     *
     * @param serviceSpecificationRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServiceSpecificationRefDTO> partialUpdate(ServiceSpecificationRefDTO serviceSpecificationRefDTO) {
        log.debug("Request to partially update ServiceSpecificationRef : {}", serviceSpecificationRefDTO);

        return serviceSpecificationRefRepository
            .findById(serviceSpecificationRefDTO.getId())
            .map(existingServiceSpecificationRef -> {
                serviceSpecificationRefMapper.partialUpdate(existingServiceSpecificationRef, serviceSpecificationRefDTO);

                return existingServiceSpecificationRef;
            })
            .map(serviceSpecificationRefRepository::save)
            .map(serviceSpecificationRefMapper::toDto);
    }

    /**
     * Get all the serviceSpecificationRefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceSpecificationRefDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceSpecificationRefs");
        return serviceSpecificationRefRepository.findAll(pageable).map(serviceSpecificationRefMapper::toDto);
    }

    /**
     * Get one serviceSpecificationRef by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceSpecificationRefDTO> findOne(String id) {
        log.debug("Request to get ServiceSpecificationRef : {}", id);
        return serviceSpecificationRefRepository.findById(id).map(serviceSpecificationRefMapper::toDto);
    }

    /**
     * Delete the serviceSpecificationRef by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ServiceSpecificationRef : {}", id);
        serviceSpecificationRefRepository.deleteById(id);
    }
}

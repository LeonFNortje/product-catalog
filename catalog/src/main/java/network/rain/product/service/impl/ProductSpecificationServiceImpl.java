package network.rain.product.service.impl;

import network.rain.product.domain.ProductSpecification;
import network.rain.product.repository.ProductSpecificationRepository;
import network.rain.product.service.ProductSpecificationService;
import network.rain.product.service.dto.ProductSpecificationDTO;
import network.rain.product.service.mapper.ProductSpecificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ProductSpecification}.
 */
@Service
@Transactional
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

    private final Logger log = LoggerFactory.getLogger(ProductSpecificationServiceImpl.class);

    private final ProductSpecificationRepository productSpecificationRepository;

    private final ProductSpecificationMapper productSpecificationMapper;

    public ProductSpecificationServiceImpl(
        ProductSpecificationRepository productSpecificationRepository,
        ProductSpecificationMapper productSpecificationMapper
    ) {
        this.productSpecificationRepository = productSpecificationRepository;
        this.productSpecificationMapper = productSpecificationMapper;
    }

    @Override
    public Mono<ProductSpecificationDTO> save(ProductSpecificationDTO productSpecificationDTO) {
        log.debug("Request to save ProductSpecification : {}", productSpecificationDTO);
        return productSpecificationRepository
            .save(productSpecificationMapper.toEntity(productSpecificationDTO))
            .map(productSpecificationMapper::toDto);
    }

    @Override
    public Mono<ProductSpecificationDTO> update(ProductSpecificationDTO productSpecificationDTO) {
        log.debug("Request to update ProductSpecification : {}", productSpecificationDTO);
        return productSpecificationRepository
            .save(productSpecificationMapper.toEntity(productSpecificationDTO).setIsPersisted())
            .map(productSpecificationMapper::toDto);
    }

    @Override
    public Mono<ProductSpecificationDTO> partialUpdate(ProductSpecificationDTO productSpecificationDTO) {
        log.debug("Request to partially update ProductSpecification : {}", productSpecificationDTO);

        return productSpecificationRepository
            .findById(productSpecificationDTO.getId())
            .map(existingProductSpecification -> {
                productSpecificationMapper.partialUpdate(existingProductSpecification, productSpecificationDTO);

                return existingProductSpecification;
            })
            .flatMap(productSpecificationRepository::save)
            .map(productSpecificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ProductSpecificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSpecifications");
        return productSpecificationRepository.findAllBy(pageable).map(productSpecificationMapper::toDto);
    }

    public Mono<Long> countAll() {
        return productSpecificationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ProductSpecificationDTO> findOne(String id) {
        log.debug("Request to get ProductSpecification : {}", id);
        return productSpecificationRepository.findById(id).map(productSpecificationMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete ProductSpecification : {}", id);
        return productSpecificationRepository.deleteById(id);
    }
}

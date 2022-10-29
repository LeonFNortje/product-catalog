package network.rain.product.service.impl;

import java.util.Optional;
import network.rain.product.domain.ProductSpecification;
import network.rain.product.repository.ProductSpecificationRepository;
import network.rain.product.service.ProductSpecificationService;
import network.rain.product.service.dto.ProductSpecificationDTO;
import network.rain.product.service.mapper.ProductSpecificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ProductSpecificationDTO save(ProductSpecificationDTO productSpecificationDTO) {
        log.debug("Request to save ProductSpecification : {}", productSpecificationDTO);
        ProductSpecification productSpecification = productSpecificationMapper.toEntity(productSpecificationDTO);
        productSpecification = productSpecificationRepository.save(productSpecification);
        return productSpecificationMapper.toDto(productSpecification);
    }

    @Override
    public ProductSpecificationDTO update(ProductSpecificationDTO productSpecificationDTO) {
        log.debug("Request to update ProductSpecification : {}", productSpecificationDTO);
        ProductSpecification productSpecification = productSpecificationMapper.toEntity(productSpecificationDTO);
        productSpecification.setIsPersisted();
        productSpecification = productSpecificationRepository.save(productSpecification);
        return productSpecificationMapper.toDto(productSpecification);
    }

    @Override
    public Optional<ProductSpecificationDTO> partialUpdate(ProductSpecificationDTO productSpecificationDTO) {
        log.debug("Request to partially update ProductSpecification : {}", productSpecificationDTO);

        return productSpecificationRepository
            .findById(productSpecificationDTO.getId())
            .map(existingProductSpecification -> {
                productSpecificationMapper.partialUpdate(existingProductSpecification, productSpecificationDTO);

                return existingProductSpecification;
            })
            .map(productSpecificationRepository::save)
            .map(productSpecificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSpecificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductSpecifications");
        return productSpecificationRepository.findAll(pageable).map(productSpecificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSpecificationDTO> findOne(String id) {
        log.debug("Request to get ProductSpecification : {}", id);
        return productSpecificationRepository.findById(id).map(productSpecificationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ProductSpecification : {}", id);
        productSpecificationRepository.deleteById(id);
    }
}

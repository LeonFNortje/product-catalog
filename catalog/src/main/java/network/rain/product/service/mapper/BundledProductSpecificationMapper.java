package network.rain.product.service.mapper;

import network.rain.product.domain.BundledProductSpecification;
import network.rain.product.service.dto.BundledProductSpecificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BundledProductSpecification} and its DTO {@link BundledProductSpecificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface BundledProductSpecificationMapper extends EntityMapper<BundledProductSpecificationDTO, BundledProductSpecification> {}

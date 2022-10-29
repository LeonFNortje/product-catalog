package network.rain.product.service.mapper;

import network.rain.product.domain.ResourceSpecificationRef;
import network.rain.product.service.dto.ResourceSpecificationRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceSpecificationRef} and its DTO {@link ResourceSpecificationRefDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResourceSpecificationRefMapper extends EntityMapper<ResourceSpecificationRefDTO, ResourceSpecificationRef> {}

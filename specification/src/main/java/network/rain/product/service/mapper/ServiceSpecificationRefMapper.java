package network.rain.product.service.mapper;

import network.rain.product.domain.ServiceSpecificationRef;
import network.rain.product.service.dto.ServiceSpecificationRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceSpecificationRef} and its DTO {@link ServiceSpecificationRefDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceSpecificationRefMapper extends EntityMapper<ServiceSpecificationRefDTO, ServiceSpecificationRef> {}

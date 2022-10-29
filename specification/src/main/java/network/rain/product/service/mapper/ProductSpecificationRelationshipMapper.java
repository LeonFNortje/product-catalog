package network.rain.product.service.mapper;

import network.rain.product.domain.ProductSpecificationRelationship;
import network.rain.product.service.dto.ProductSpecificationRelationshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSpecificationRelationship} and its DTO {@link ProductSpecificationRelationshipDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductSpecificationRelationshipMapper
    extends EntityMapper<ProductSpecificationRelationshipDTO, ProductSpecificationRelationship> {}

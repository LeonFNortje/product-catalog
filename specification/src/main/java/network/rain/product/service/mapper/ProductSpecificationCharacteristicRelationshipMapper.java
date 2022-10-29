package network.rain.product.service.mapper;

import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import network.rain.product.service.dto.ProductSpecificationCharacteristicRelationshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSpecificationCharacteristicRelationship} and its DTO {@link ProductSpecificationCharacteristicRelationshipDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductSpecificationCharacteristicRelationshipMapper
    extends EntityMapper<ProductSpecificationCharacteristicRelationshipDTO, ProductSpecificationCharacteristicRelationship> {}

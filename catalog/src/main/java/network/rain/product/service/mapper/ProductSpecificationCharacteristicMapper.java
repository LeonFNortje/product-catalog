package network.rain.product.service.mapper;

import network.rain.product.domain.ProductSpecificationCharacteristic;
import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import network.rain.product.service.dto.ProductSpecificationCharacteristicDTO;
import network.rain.product.service.dto.ProductSpecificationCharacteristicRelationshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSpecificationCharacteristic} and its DTO {@link ProductSpecificationCharacteristicDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductSpecificationCharacteristicMapper
    extends EntityMapper<ProductSpecificationCharacteristicDTO, ProductSpecificationCharacteristic> {
    @Mapping(
        target = "productSpecificationCharacteristicRelationship",
        source = "productSpecificationCharacteristicRelationship",
        qualifiedByName = "productSpecificationCharacteristicRelationshipId"
    )
    ProductSpecificationCharacteristicDTO toDto(ProductSpecificationCharacteristic s);

    @Named("productSpecificationCharacteristicRelationshipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductSpecificationCharacteristicRelationshipDTO toDtoProductSpecificationCharacteristicRelationshipId(
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship
    );
}

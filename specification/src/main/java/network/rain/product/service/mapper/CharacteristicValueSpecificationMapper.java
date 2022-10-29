package network.rain.product.service.mapper;

import network.rain.product.domain.CharacteristicValueSpecification;
import network.rain.product.domain.ProductSpecificationCharacteristicRelationship;
import network.rain.product.service.dto.CharacteristicValueSpecificationDTO;
import network.rain.product.service.dto.ProductSpecificationCharacteristicRelationshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CharacteristicValueSpecification} and its DTO {@link CharacteristicValueSpecificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface CharacteristicValueSpecificationMapper
    extends EntityMapper<CharacteristicValueSpecificationDTO, CharacteristicValueSpecification> {
    @Mapping(
        target = "productSpecificationCharacteristicRelationship",
        source = "productSpecificationCharacteristicRelationship",
        qualifiedByName = "productSpecificationCharacteristicRelationshipId"
    )
    CharacteristicValueSpecificationDTO toDto(CharacteristicValueSpecification s);

    @Named("productSpecificationCharacteristicRelationshipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductSpecificationCharacteristicRelationshipDTO toDtoProductSpecificationCharacteristicRelationshipId(
        ProductSpecificationCharacteristicRelationship productSpecificationCharacteristicRelationship
    );
}

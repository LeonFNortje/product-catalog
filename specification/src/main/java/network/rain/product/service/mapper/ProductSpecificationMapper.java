package network.rain.product.service.mapper;

import network.rain.product.domain.AttachmentRefOrValue;
import network.rain.product.domain.BundledProductSpecification;
import network.rain.product.domain.ProductSpecification;
import network.rain.product.domain.ProductSpecificationCharacteristic;
import network.rain.product.domain.ProductSpecificationRelationship;
import network.rain.product.domain.RelatedParty;
import network.rain.product.domain.ResourceSpecificationRef;
import network.rain.product.domain.ServiceSpecificationRef;
import network.rain.product.domain.TargetProductSchema;
import network.rain.product.service.dto.AttachmentRefOrValueDTO;
import network.rain.product.service.dto.BundledProductSpecificationDTO;
import network.rain.product.service.dto.ProductSpecificationCharacteristicDTO;
import network.rain.product.service.dto.ProductSpecificationDTO;
import network.rain.product.service.dto.ProductSpecificationRelationshipDTO;
import network.rain.product.service.dto.RelatedPartyDTO;
import network.rain.product.service.dto.ResourceSpecificationRefDTO;
import network.rain.product.service.dto.ServiceSpecificationRefDTO;
import network.rain.product.service.dto.TargetProductSchemaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSpecification} and its DTO {@link ProductSpecificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductSpecificationMapper extends EntityMapper<ProductSpecificationDTO, ProductSpecification> {
    @Mapping(target = "targetProductSchema", source = "targetProductSchema", qualifiedByName = "targetProductSchemaId")
    @Mapping(target = "resourceSpecificationRef", source = "resourceSpecificationRef", qualifiedByName = "resourceSpecificationRefId")
    @Mapping(target = "attachmentRefOrValue", source = "attachmentRefOrValue", qualifiedByName = "attachmentRefOrValueId")
    @Mapping(target = "relatedParty", source = "relatedParty", qualifiedByName = "relatedPartyId")
    @Mapping(target = "serviceSpecificationRef", source = "serviceSpecificationRef", qualifiedByName = "serviceSpecificationRefId")
    @Mapping(
        target = "productSpecificationRelationship",
        source = "productSpecificationRelationship",
        qualifiedByName = "productSpecificationRelationshipId"
    )
    @Mapping(
        target = "bundledProductSpecification",
        source = "bundledProductSpecification",
        qualifiedByName = "bundledProductSpecificationId"
    )
    @Mapping(
        target = "productSpecificationCharacteristic",
        source = "productSpecificationCharacteristic",
        qualifiedByName = "productSpecificationCharacteristicId"
    )
    ProductSpecificationDTO toDto(ProductSpecification s);

    @Named("targetProductSchemaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TargetProductSchemaDTO toDtoTargetProductSchemaId(TargetProductSchema targetProductSchema);

    @Named("resourceSpecificationRefId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceSpecificationRefDTO toDtoResourceSpecificationRefId(ResourceSpecificationRef resourceSpecificationRef);

    @Named("attachmentRefOrValueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachmentRefOrValueDTO toDtoAttachmentRefOrValueId(AttachmentRefOrValue attachmentRefOrValue);

    @Named("relatedPartyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RelatedPartyDTO toDtoRelatedPartyId(RelatedParty relatedParty);

    @Named("serviceSpecificationRefId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceSpecificationRefDTO toDtoServiceSpecificationRefId(ServiceSpecificationRef serviceSpecificationRef);

    @Named("productSpecificationRelationshipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductSpecificationRelationshipDTO toDtoProductSpecificationRelationshipId(
        ProductSpecificationRelationship productSpecificationRelationship
    );

    @Named("bundledProductSpecificationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BundledProductSpecificationDTO toDtoBundledProductSpecificationId(BundledProductSpecification bundledProductSpecification);

    @Named("productSpecificationCharacteristicId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductSpecificationCharacteristicDTO toDtoProductSpecificationCharacteristicId(
        ProductSpecificationCharacteristic productSpecificationCharacteristic
    );
}

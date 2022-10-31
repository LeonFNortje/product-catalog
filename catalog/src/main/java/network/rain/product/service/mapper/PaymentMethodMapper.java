package network.rain.product.service.mapper;

import network.rain.product.domain.PaymentMethod;
import network.rain.product.domain.RelatedParty;
import network.rain.product.domain.RelatedPlace;
import network.rain.product.service.dto.PaymentMethodDTO;
import network.rain.product.service.dto.RelatedPartyDTO;
import network.rain.product.service.dto.RelatedPlaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentMethod} and its DTO {@link PaymentMethodDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMethodMapper extends EntityMapper<PaymentMethodDTO, PaymentMethod> {
    @Mapping(target = "relatedPlace", source = "relatedPlace", qualifiedByName = "relatedPlaceId")
    @Mapping(target = "relatedParty", source = "relatedParty", qualifiedByName = "relatedPartyId")
    PaymentMethodDTO toDto(PaymentMethod s);

    @Named("relatedPlaceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RelatedPlaceDTO toDtoRelatedPlaceId(RelatedPlace relatedPlace);

    @Named("relatedPartyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RelatedPartyDTO toDtoRelatedPartyId(RelatedParty relatedParty);
}

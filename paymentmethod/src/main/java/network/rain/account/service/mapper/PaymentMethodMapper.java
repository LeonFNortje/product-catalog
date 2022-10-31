package network.rain.account.service.mapper;

import network.rain.account.domain.PaymentMethod;
import network.rain.account.domain.RelatedParty;
import network.rain.account.domain.RelatedPlace;
import network.rain.account.service.dto.PaymentMethodDTO;
import network.rain.account.service.dto.RelatedPartyDTO;
import network.rain.account.service.dto.RelatedPlaceDTO;
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

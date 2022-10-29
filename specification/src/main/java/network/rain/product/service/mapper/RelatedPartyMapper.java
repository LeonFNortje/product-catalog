package network.rain.product.service.mapper;

import network.rain.product.domain.RelatedParty;
import network.rain.product.service.dto.RelatedPartyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelatedParty} and its DTO {@link RelatedPartyDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelatedPartyMapper extends EntityMapper<RelatedPartyDTO, RelatedParty> {}

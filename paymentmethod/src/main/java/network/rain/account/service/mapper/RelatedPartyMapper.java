package network.rain.account.service.mapper;

import network.rain.account.domain.RelatedParty;
import network.rain.account.service.dto.RelatedPartyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelatedParty} and its DTO {@link RelatedPartyDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelatedPartyMapper extends EntityMapper<RelatedPartyDTO, RelatedParty> {}

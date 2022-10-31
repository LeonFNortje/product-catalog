package network.rain.account.service.mapper;

import network.rain.account.domain.RelatedPlace;
import network.rain.account.service.dto.RelatedPlaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelatedPlace} and its DTO {@link RelatedPlaceDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelatedPlaceMapper extends EntityMapper<RelatedPlaceDTO, RelatedPlace> {}

package network.rain.product.service.mapper;

import network.rain.product.domain.RelatedPlace;
import network.rain.product.service.dto.RelatedPlaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelatedPlace} and its DTO {@link RelatedPlaceDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelatedPlaceMapper extends EntityMapper<RelatedPlaceDTO, RelatedPlace> {}

package network.rain.product.service.mapper;

import network.rain.product.domain.AttachmentRefOrValue;
import network.rain.product.service.dto.AttachmentRefOrValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentRefOrValue} and its DTO {@link AttachmentRefOrValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentRefOrValueMapper extends EntityMapper<AttachmentRefOrValueDTO, AttachmentRefOrValue> {}

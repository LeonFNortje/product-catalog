package network.rain.product.service.mapper;

import network.rain.product.domain.TargetProductSchema;
import network.rain.product.service.dto.TargetProductSchemaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TargetProductSchema} and its DTO {@link TargetProductSchemaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TargetProductSchemaMapper extends EntityMapper<TargetProductSchemaDTO, TargetProductSchema> {}

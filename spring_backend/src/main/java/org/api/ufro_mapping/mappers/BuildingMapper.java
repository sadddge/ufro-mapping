package org.api.ufro_mapping.mappers;

import org.api.ufro_mapping.dto.request.update.BuildingUpdateDTO;
import org.api.ufro_mapping.models.Building;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(BuildingUpdateDTO dto, @MappingTarget Building entity);
}

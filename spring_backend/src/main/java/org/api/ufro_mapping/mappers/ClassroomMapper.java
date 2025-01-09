package org.api.ufro_mapping.mappers;

import org.api.ufro_mapping.dto.request.update.ClassroomUpdateDTO;
import org.api.ufro_mapping.models.Classroom;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ClassroomUpdateDTO dto, @MappingTarget Classroom entity);
}

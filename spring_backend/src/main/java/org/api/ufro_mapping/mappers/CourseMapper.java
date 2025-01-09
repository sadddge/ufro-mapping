package org.api.ufro_mapping.mappers;

import org.api.ufro_mapping.dto.request.update.CourseUpdateDTO;
import org.api.ufro_mapping.models.Course;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CourseUpdateDTO dto, @MappingTarget Course entity);
}

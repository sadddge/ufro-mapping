package org.api.ufro_mapping.mappers;

import org.api.ufro_mapping.dto.request.update.LectureUpdateDTO;
import org.api.ufro_mapping.models.Lecture;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(LectureUpdateDTO dto, @MappingTarget Lecture entity);
}

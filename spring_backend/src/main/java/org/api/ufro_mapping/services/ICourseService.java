package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.request.CourseRequestDTO;
import org.api.ufro_mapping.dto.request.update.CourseUpdateDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<CourseDTO> findAll();
    Optional<CourseDTO> findById(Long id);
    CourseDTO save(CourseRequestDTO courseRequestDTO);
    Optional<CourseDTO> update(Long id, CourseUpdateDTO courseRequestDTO);
    boolean delete(Long id);
}

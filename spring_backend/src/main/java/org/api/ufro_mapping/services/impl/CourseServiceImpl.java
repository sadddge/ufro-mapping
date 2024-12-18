package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.request.CourseRequestDTO;
import org.api.ufro_mapping.dto.request.update.CourseUpdateDTO;
import org.api.ufro_mapping.dto.response.ClassroomDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.LectureDTO;
import org.api.ufro_mapping.mappers.CourseMapper;
import org.api.ufro_mapping.models.Course;
import org.api.ufro_mapping.repositories.CourseRepository;
import org.api.ufro_mapping.services.ICourseService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements ICourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }
    @Override
    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public Optional<CourseDTO> findById(Long id) {
        return courseRepository.findById(id).map(this::entityToDTO);
    }

    @Override
    public CourseDTO save(CourseRequestDTO courseRequestDTO) {
        Course course = new Course();
        course.setName(courseRequestDTO.getName());
        course.setCode(courseRequestDTO.getCode());
        course.setDescription(courseRequestDTO.getDescription());
        course.setSct(courseRequestDTO.getSct());
        return entityToDTO(courseRepository.save(course));
    }

    @Override
    public Optional<CourseDTO> update(Long id, CourseUpdateDTO courseRequestDTO) {
        return courseRepository.findById(id).map(course -> {
            courseMapper.updateEntityFromDTO(courseRequestDTO, course);
            return entityToDTO(courseRepository.save(course));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CourseDTO entityToDTO(Course course) {
        return CourseDTO.builder().
                id(course.getId()).
                name(course.getName()).
                code(course.getCode()).
                description(course.getDescription()).
                sct(course.getSct()).
                lectures(Optional.ofNullable(course.getLectures())
                        .orElse(Collections.emptySet())
                        .stream().map(lecture -> LectureDTO.builder().
                        id(lecture.getId()).
                        dayOfWeek(lecture.getDayOfWeek()).
                        module(lecture.getModule()).
                        classroom(ClassroomDTO.builder()
                                .name(lecture.getClassroom().getName())
                                .build())
                        .period(lecture.getPeriod())
                                .build()).collect(Collectors.toSet()))
                .build();
    }
}

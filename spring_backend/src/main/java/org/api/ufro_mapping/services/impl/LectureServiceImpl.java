package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.request.LectureRequestDTO;
import org.api.ufro_mapping.dto.request.update.LectureUpdateDTO;
import org.api.ufro_mapping.dto.response.BuildingDTO;
import org.api.ufro_mapping.dto.response.ClassroomDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.LectureDTO;
import org.api.ufro_mapping.mappers.LectureMapper;
import org.api.ufro_mapping.models.Classroom;
import org.api.ufro_mapping.models.Course;
import org.api.ufro_mapping.models.Lecture;
import org.api.ufro_mapping.repositories.ClassroomRepository;
import org.api.ufro_mapping.repositories.CourseRepository;
import org.api.ufro_mapping.repositories.LectureRepository;
import org.api.ufro_mapping.services.ILectureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements ILectureService {
    private final LectureRepository lectureRepository;
    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;
    private final LectureMapper lectureMapper;
    public LectureServiceImpl(LectureRepository lectureRepository, ClassroomRepository classroomRepository, CourseRepository courseRepository, LectureMapper lectureMapper) {
        this.lectureRepository = lectureRepository;
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
        this.lectureMapper = lectureMapper;
    }

    @Override
    public List<LectureDTO> findAll() {
        return lectureRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public Optional<LectureDTO> findById(Long id) {
        return lectureRepository.findById(id).map(this::entityToDTO);
    }

    @Override
    public LectureDTO save(LectureRequestDTO lectureDTO) {
        Optional<Classroom> classroom = classroomRepository.findById(lectureDTO.getClassroomId());
        Optional<Course> course = courseRepository.findById(lectureDTO.getCourseId());
        if (classroom.isEmpty()) {
            throw new RuntimeException("Classroom not found");
        } else if (course.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        Lecture lecture = requestToEntity(lectureDTO, classroom.get(), course.get());
        return entityToDTO(lectureRepository.save(lecture));
    }

    @Override
    public Optional<LectureDTO> update(Long id, LectureUpdateDTO lectureDTO) {
        return lectureRepository.findById(id).map(lecture -> {
            Optional<Course> course = validateUpdateCourse(lectureDTO.getCourseId());
            Optional<Classroom> classroom = validateUpdateClassroom(lectureDTO.getClassroomId());
            course.ifPresent(lecture::setCourse);
            classroom.ifPresent(lecture::setClassroom);
            lectureMapper.updateEntityFromDTO(lectureDTO, lecture);
            return entityToDTO(lectureRepository.save(lecture));
        });
    }

    @Override
    public boolean deleteById(Long id) {
        if (lectureRepository.existsById(id)) {
            lectureRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Optional<Course> validateUpdateCourse(Long courseId) {
        if (courseId == null) {
            return Optional.empty();
        }
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        return course;
    }

    private Optional<Classroom> validateUpdateClassroom(Long classroomId) {
        if (classroomId == null) {
            return Optional.empty();
        }
        Optional<Classroom> classroom = classroomRepository.findById(classroomId);
        if (classroom.isEmpty()) {
            throw new RuntimeException("Classroom not found");
        }
        return classroom;
    }

    private LectureDTO entityToDTO(Lecture lecture) {
        return LectureDTO.builder()
                .id(lecture.getId())
                .dayOfWeek(lecture.getDayOfWeek())
                .period(lecture.getPeriod())
                .teacher(lecture.getTeacherName())
                .module(lecture.getModule())
                .classroom(ClassroomDTO.builder()
                        .id(lecture.getClassroom().getId())
                        .name(lecture.getClassroom().getName())
                        .building(BuildingDTO.builder()
                                .id(lecture.getClassroom().getBuilding().getId())
                                .name(lecture.getClassroom().getBuilding().getName())
                                .alias(lecture.getClassroom().getBuilding().getAlias())
                                .build())
                        .build())
                .course(CourseDTO.builder()
                        .id(lecture.getCourse().getId())
                        .name(lecture.getCourse().getName())
                        .build())
                .build();
    }

    private Lecture requestToEntity(LectureRequestDTO lectureDTO, Classroom classroom, Course course) {
        Lecture lecture = new Lecture();
        lecture.setDayOfWeek(lectureDTO.getDayOfWeek());
        lecture.setPeriod(lectureDTO.getPeriod());
        lecture.setTeacherName(lectureDTO.getTeacher());
        lecture.setModule(lectureDTO.getModule());
        lecture.setClassroom(classroom);
        lecture.setCourse(course);
        return lecture;
    }

}

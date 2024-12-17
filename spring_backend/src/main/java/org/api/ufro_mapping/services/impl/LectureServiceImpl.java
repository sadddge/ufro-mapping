package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.request.LectureRequestDTO;
import org.api.ufro_mapping.dto.request.update.LectureUpdateDTO;
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
            Optional<Classroom> classroom = classroomRepository.findById(lectureDTO.getClassroomId());
            Optional<Course> course = courseRepository.findById(lectureDTO.getCourseId());
            if (classroom.isEmpty() || course.isEmpty()) {
                throw new RuntimeException("Classroom or Course not found");
            }
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

    private LectureDTO entityToDTO(Lecture lecture) {
        return LectureDTO.builder()
                .id(lecture.getId())
                .dayOfWeek(lecture.getDayOfWeek())
                .period(lecture.getPeriod())
                .teacher(lecture.getTeacherName())
                .module(lecture.getModule())
                .classroom(ClassroomDTO.builder()
                        .name(lecture.getClassroom().getName())
                        .build())
                .course(CourseDTO.builder()
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

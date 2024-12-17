package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.response.ScheduleClassDTO;
import org.api.ufro_mapping.models.Classroom;
import org.api.ufro_mapping.models.Course;
import org.api.ufro_mapping.models.Lecture;
import org.api.ufro_mapping.models.User;
import org.api.ufro_mapping.repositories.ClassroomRepository;
import org.api.ufro_mapping.repositories.CourseRepository;
import org.api.ufro_mapping.repositories.UserRepository;
import org.api.ufro_mapping.services.IScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    public ScheduleServiceImpl(ClassroomRepository classroomRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    @Override
    public List<ScheduleClassDTO> getScheduleByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User userFound = user.get();
        Set<Course> courses = userFound.getCourses();
        List<Lecture> lectures = courses.stream()
                .map(Course::getLectures)
                .flatMap(Set::stream)
                .toList();
        return lectures.stream()
                .map(this::lectureToScheduleClassDTO)
                .toList();
    }

    @Override
    public List<ScheduleClassDTO> getScheduleByClassroomId(Long classroomId) {
        Optional<Classroom> classroom = classroomRepository.findById(classroomId);
        if (classroom.isEmpty()) {
            throw new RuntimeException("Classroom not found");
        }
        Classroom classroomFound = classroom.get();
        Set<Lecture> lectures = classroomFound.getLectures();
        return lectures.stream()
                .map(this::lectureToScheduleClassDTO)
                .toList();
    }

    @Override
    public List<ScheduleClassDTO> getScheduleByCourseId(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        Course courseFound = course.get();
        Set<Lecture> lectures = courseFound.getLectures();
        return lectures.stream()
                .map(this::lectureToScheduleClassDTO)
                .toList();
    }

    private ScheduleClassDTO lectureToScheduleClassDTO(Lecture lecture) {
        return ScheduleClassDTO.builder()
                .id(lecture.getId())
                .courseName(lecture.getCourse().getName())
                .classroomName(lecture.getClassroom().getName())
                .code(lecture.getCourse().getCode())
                .day(lecture.getDayOfWeek())
                .period(lecture.getPeriod())
                .module(lecture.getModule())
                .build();
    }
}

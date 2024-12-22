package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.request.update.UserUpdateDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.UserDTO;
import org.api.ufro_mapping.mappers.UserMapper;
import org.api.ufro_mapping.models.Course;
import org.api.ufro_mapping.models.User;
import org.api.ufro_mapping.repositories.CourseRepository;
import org.api.ufro_mapping.repositories.UserRepository;
import org.api.ufro_mapping.services.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, CourseRepository courseRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(this::entityToDTO);
    }


    @Override
    public Optional<UserDTO> update(Long id, UserUpdateDTO userRegisterDTO) {
        return userRepository.findById(id).map(user -> {
            userMapper.updateEntityFromDTO(userRegisterDTO, user);
            return entityToDTO(userRepository.save(user));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CourseDTO> getCoursesByUserId(Long id) {
        return userRepository.findById(id)
                .map(user -> user.getCourses().stream().map(course -> CourseDTO.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .build()).toList())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void addInscription(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        user.getCourses().add(course);
        userRepository.save(user);
    }

    @Override
    public boolean deleteInscription(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        user.getCourses().remove(course);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean validateUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName(); // Nombre del usuario autenticado
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return true;
        }
        User authenticatedUser = userRepository.findByName(authenticatedUsername)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        return authenticatedUser.getId().equals(userId);
    }

    private UserDTO entityToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}

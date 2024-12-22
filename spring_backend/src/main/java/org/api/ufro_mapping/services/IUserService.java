package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.request.update.UserUpdateDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    Optional<UserDTO> update(Long id, UserUpdateDTO userRegisterDTO);
    boolean delete(Long id);
    List<CourseDTO> getCoursesByUserId(Long id);
    void addInscription(Long userId, Long courseId);
    boolean deleteInscription(Long userId, Long courseId);
    boolean validateUser(Long userId);
}

package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.request.UserRegisterDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    Optional<UserDTO> update(Long id, UserRegisterDTO userRegisterDTO);
    boolean delete(Long id);
    List<CourseDTO> getCoursesByUserId(Long id);
    boolean deleteInscription(Long userId, Long courseId);
}

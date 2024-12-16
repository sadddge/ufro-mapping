package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.request.UserRegisterDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.UserDTO;
import org.api.ufro_mapping.models.User;
import org.api.ufro_mapping.repositories.UserRepository;
import org.api.ufro_mapping.services.IUserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public Optional<UserDTO> update(Long id, UserRegisterDTO userRegisterDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName(); // Nombre del usuario autenticado
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            User authenticatedUser = userRepository.findByName(authenticatedUsername)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

            if (!authenticatedUser.getId().equals(id)) {
                throw new AccessDeniedException("You do not have permission to update this user.");
            }
        }
        return userRepository.findById(id).map(user -> {
            user.setName(userRegisterDTO.getName());
            user.setEmail(userRegisterDTO.getEmail());
            user.setPassword(userRegisterDTO.getPassword());
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
        return null;
    }

    @Override
    public boolean deleteInscription(Long userId, Long courseId) {
        return false;
    }

    private UserDTO entityToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}

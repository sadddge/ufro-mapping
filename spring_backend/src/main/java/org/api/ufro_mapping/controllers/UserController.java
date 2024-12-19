package org.api.ufro_mapping.controllers;

import jakarta.validation.Valid;
import org.api.ufro_mapping.dto.request.update.UserUpdateDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.ScheduleClassDTO;
import org.api.ufro_mapping.dto.response.UserDTO;
import org.api.ufro_mapping.services.IScheduleService;
import org.api.ufro_mapping.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;
    private final IScheduleService scheduleService;

    public UserController(IUserService userService, IScheduleService scheduleService) {
        this.userService = userService;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/courses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDTO>> getCoursesByUserId(@PathVariable Long id) {
        List<CourseDTO> courses = userService.getCoursesByUserId(id);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}/schedule")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ScheduleClassDTO>> getScheduleByUserId(@PathVariable Long id) {
        if (!userService.validateUser(id)) {
            return ResponseEntity.status(401).build();
        }
        List<ScheduleClassDTO> schedule = scheduleService.getScheduleByUserId(id);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        if (userService.validateUser(id)) {
            return userService.update(id, userUpdateDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(401).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}/courses/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long userId, @PathVariable Long courseId) {
        if (userService.validateUser(userId)) {
            return userService.deleteInscription(userId, courseId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/{id}/courses/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> addInscription(@PathVariable Long id, @PathVariable Long courseId) {
        if (userService.validateUser(id)) {
            userService.addInscription(id, courseId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(401).build();
    }
}

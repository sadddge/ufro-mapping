package org.api.ufro_mapping.controllers;

import jakarta.validation.Valid;
import org.api.ufro_mapping.dto.request.CourseRequestDTO;
import org.api.ufro_mapping.dto.request.update.CourseUpdateDTO;
import org.api.ufro_mapping.dto.response.CourseDTO;
import org.api.ufro_mapping.dto.response.ScheduleClassDTO;
import org.api.ufro_mapping.services.ICourseService;
import org.api.ufro_mapping.services.IScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final ICourseService courseService;
    private final IScheduleService scheduleService;

    public CourseController(ICourseService courseService, IScheduleService scheduleService) {
        this.courseService = courseService;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDTO>> findAll() {
        List<CourseDTO> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDTO> findById(@PathVariable Long id) {
        return courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/schedule")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ScheduleClassDTO>> getScheduleByCourseId(@PathVariable Long id) {
        List<ScheduleClassDTO> schedules = scheduleService.getScheduleByCourseId(id);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> save(@Valid @RequestBody CourseRequestDTO courseDTO) {
        return ResponseEntity.ok(courseService.save(courseDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> update(@PathVariable Long id, @Valid @RequestBody CourseUpdateDTO courseDTO) {
        return courseService.update(id, courseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return courseService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
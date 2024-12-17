package org.api.ufro_mapping.controllers;

import jakarta.validation.Valid;
import org.api.ufro_mapping.dto.request.ClassroomRequestDTO;
import org.api.ufro_mapping.dto.response.ClassroomDTO;
import org.api.ufro_mapping.dto.response.ScheduleClassDTO;
import org.api.ufro_mapping.services.IClassroomService;
import org.api.ufro_mapping.services.IScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
    private final IClassroomService classroomService;
    private final IScheduleService scheduleService;

    public ClassroomController(IClassroomService classroomService, IScheduleService scheduleService) {
        this.classroomService = classroomService;
        this.scheduleService = scheduleService;
    }
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClassroomDTO>> findAll() {
        List<ClassroomDTO> classrooms = classroomService.findAll();
        return ResponseEntity.ok(classrooms);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClassroomDTO> findById(@PathVariable Long id) {
        return classroomService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/schedule")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ScheduleClassDTO>> getScheduleByClassroomId(@PathVariable Long id) {
        List<ScheduleClassDTO> schedules = scheduleService.getScheduleByClassroomId(id);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> save(@Valid @RequestBody ClassroomRequestDTO classroomDTO) {
        return ResponseEntity.ok(classroomService.save(classroomDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> update(@PathVariable Long id, @Valid @RequestBody ClassroomRequestDTO classroomDTO) {
        return classroomService.update(id, classroomDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return classroomService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

package org.api.ufro_mapping.controllers;

import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.api.ufro_mapping.dto.request.BuildingRequestDTO;
import org.api.ufro_mapping.dto.request.update.BuildingUpdateDTO;
import org.api.ufro_mapping.dto.response.BuildingDTO;
import org.api.ufro_mapping.dto.response.ClassroomDTO;
import org.api.ufro_mapping.services.IBuildingService;
import org.api.ufro_mapping.services.IClassroomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log
@RestController
@RequestMapping("/api/buildings")
public class BuildingController {
    private final IBuildingService buildingService;
    private final IClassroomService classroomService;

    public BuildingController(IBuildingService buildingService, IClassroomService classroomService) {
        this.buildingService = buildingService;
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<BuildingDTO>> findAll() {
        List<BuildingDTO> buildings = buildingService.findAll();
        return ResponseEntity.ok(buildings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingDTO> findById(@PathVariable Long id) {
        return buildingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/classrooms")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClassroomDTO>> getClassroomsByBuildingId(@PathVariable Long id) {
        List<ClassroomDTO> classrooms = classroomService.findByBuildingId(id);
        return ResponseEntity.ok(classrooms);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BuildingDTO> save(@Valid @RequestBody BuildingRequestDTO buildingDTO) {
        return ResponseEntity.ok(buildingService.save(buildingDTO));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BuildingDTO> update(@PathVariable Long id, @Valid @RequestBody BuildingUpdateDTO buildingDTO) {
        return buildingService.update(id, buildingDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return buildingService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

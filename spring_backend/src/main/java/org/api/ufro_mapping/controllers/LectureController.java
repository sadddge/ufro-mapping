package org.api.ufro_mapping.controllers;

import jakarta.validation.Valid;
import org.api.ufro_mapping.dto.request.LectureRequestDTO;
import org.api.ufro_mapping.dto.request.update.LectureUpdateDTO;
import org.api.ufro_mapping.dto.response.LectureDTO;
import org.api.ufro_mapping.services.ILectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {
    private final ILectureService lectureService;
    public LectureController(ILectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<LectureDTO>> findAll() {
        List<LectureDTO> lectures = lectureService.findAll();
        return ResponseEntity.ok(lectures);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LectureDTO> findById(@PathVariable Long id) {
        return lectureService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LectureDTO> save(@Valid @RequestBody LectureRequestDTO lectureRequestDTO) {
        return ResponseEntity.ok(lectureService.save(lectureRequestDTO));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LectureDTO> update(@PathVariable Long id, @Valid @RequestBody LectureUpdateDTO lectureRequestDTO) {
        return lectureService.update(id, lectureRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return lectureService.deleteById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

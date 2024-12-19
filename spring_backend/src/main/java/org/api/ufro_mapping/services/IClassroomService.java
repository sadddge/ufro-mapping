package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.request.ClassroomRequestDTO;
import org.api.ufro_mapping.dto.request.update.ClassroomUpdateDTO;
import org.api.ufro_mapping.dto.response.ClassroomDTO;

import java.util.List;
import java.util.Optional;

public interface IClassroomService {
    List<ClassroomDTO> findAll();
    List<ClassroomDTO> findByBuildingId(Long buildingId);
    Optional<ClassroomDTO> findById(Long id);
    ClassroomDTO save(ClassroomRequestDTO classroomDTO);
    Optional<ClassroomDTO> update(Long id, ClassroomUpdateDTO classroomDTO);
    boolean delete(Long id);
}

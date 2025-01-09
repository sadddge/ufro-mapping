package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.request.ClassroomRequestDTO;
import org.api.ufro_mapping.dto.request.update.ClassroomUpdateDTO;
import org.api.ufro_mapping.dto.response.BuildingDTO;
import org.api.ufro_mapping.dto.response.ClassroomDTO;
import org.api.ufro_mapping.dto.response.LectureDTO;
import org.api.ufro_mapping.mappers.ClassroomMapper;
import org.api.ufro_mapping.models.Building;
import org.api.ufro_mapping.models.Classroom;
import org.api.ufro_mapping.repositories.BuildingRepository;
import org.api.ufro_mapping.repositories.ClassroomRepository;
import org.api.ufro_mapping.services.IClassroomService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ClassroomServiceImpl implements IClassroomService {

    private final ClassroomRepository classroomRepository;
    private final BuildingRepository buildingRepository;
    private final ClassroomMapper classroomMapper;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, BuildingRepository buildingRepository, ClassroomMapper classroomMapper) {
        this.classroomRepository = classroomRepository;
        this.buildingRepository = buildingRepository;
        this.classroomMapper = classroomMapper;
    }

    @Override
    public List<ClassroomDTO> findAll() {
        return classroomRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public List<ClassroomDTO> findByBuildingId(Long buildingId) {
        return classroomRepository.findByBuildingId(buildingId).stream().map(this::entityToDTO).toList();
    }

    @Override
    public Optional<ClassroomDTO> findById(Long id) {
        return classroomRepository.findById(id).map(this::entityToDTO);
    }

    @Override
    public ClassroomDTO save(ClassroomRequestDTO classroomDTO) {
        Optional<Building> building = buildingRepository.findById(classroomDTO.getBuildingId());
        if (building.isEmpty()) {
            throw new RuntimeException("Building not found");
        }
        Building buildingEntity = building.get();
        Classroom classroom = new Classroom();
        classroom.setName(classroomDTO.getName());
        classroom.setBuilding(buildingEntity);
        return entityToDTO(classroomRepository.save(classroom));
    }

    @Override
    public Optional<ClassroomDTO> update(Long id, ClassroomUpdateDTO classroomDTO) {
        return classroomRepository.findById(id).map(classroom -> {
            Optional<Building> building = validateBuilding(classroomDTO.getBuildingId());
            building.ifPresent(classroom::setBuilding);
            classroomMapper.updateEntityFromDTO(classroomDTO, classroom);
            return entityToDTO(classroomRepository.save(classroom));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (classroomRepository.existsById(id)) {
            classroomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Optional<Building> validateBuilding(Long buildingId) {
        if (buildingId == null) {
            return Optional.empty();
        }
        Optional<Building> building = buildingRepository.findById(buildingId);
        if (building.isEmpty()) {
            throw new RuntimeException("Building not found");
        }
        return building;
    }

    private ClassroomDTO entityToDTO(Classroom classroom) {
        return ClassroomDTO.builder()
                .id(classroom.getId())
                .name(classroom.getName())
                .building(BuildingDTO.builder()
                        .id(classroom.getBuilding().getId())
                        .name(classroom.getBuilding().getName())
                        .alias(classroom.getBuilding().getAlias())
                        .build())
                .lectures(Optional.ofNullable(classroom.getLectures())
                        .orElse(Collections.emptySet())
                        .stream().map(lecture -> LectureDTO.builder()
                        .id(lecture.getId())
                        .dayOfWeek(lecture.getDayOfWeek())
                        .period(lecture.getPeriod())
                        .module(lecture.getModule())
                        .build()).collect(Collectors.toSet()))
                .build();
    }
}

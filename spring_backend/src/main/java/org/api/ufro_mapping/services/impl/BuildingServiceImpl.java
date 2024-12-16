package org.api.ufro_mapping.services.impl;

import org.api.ufro_mapping.dto.request.BuildingRequestDTO;
import org.api.ufro_mapping.dto.response.BuildingDTO;
import org.api.ufro_mapping.dto.response.ClassroomDTO;
import org.api.ufro_mapping.dto.response.LectureDTO;
import org.api.ufro_mapping.dto.response.LocationDTO;
import org.api.ufro_mapping.models.Building;
import org.api.ufro_mapping.repositories.BuildingRepository;
import org.api.ufro_mapping.services.IBuildingService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class BuildingServiceImpl implements IBuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }
    @Override
    public List<BuildingDTO> findAll() {
        return buildingRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public Optional<BuildingDTO> findById(Long id) {
        return buildingRepository.findById(id).map(this::entityToDTO);
    }

    @Override
    public BuildingDTO save(BuildingRequestDTO buildingRequestDTO) {
        Building building = new Building();
        building.setName(buildingRequestDTO.getName());
        building.setAlias(buildingRequestDTO.getAlias());
        building.setType(buildingRequestDTO.getType());
        building.setLatitude(buildingRequestDTO.getLatitude());
        building.setLongitude(buildingRequestDTO.getLongitude());
        return entityToDTO(buildingRepository.save(building));
    }

    @Override
    public Optional<BuildingDTO> update(Long id, BuildingRequestDTO buildingRequestDTO) {
        return buildingRepository.findById(id).map(building -> {
            building.setName(buildingRequestDTO.getName());
            building.setAlias(buildingRequestDTO.getAlias());
            building.setType(buildingRequestDTO.getType());
            building.setLatitude(buildingRequestDTO.getLatitude());
            building.setLongitude(buildingRequestDTO.getLongitude());
            return entityToDTO(buildingRepository.save(building));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (buildingRepository.existsById(id)) {
            buildingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<LocationDTO> findAllLocations() {
        return buildingRepository.findAll().stream().map(building -> LocationDTO.builder()
                .id(building.getId())
                .name(building.getName())
                .alias(building.getAlias())
                .latitude(building.getLatitude())
                .longitude(building.getLongitude())
                .build()).collect(Collectors.toList());
    }

    private BuildingDTO entityToDTO(Building building) {
        return BuildingDTO.builder()
                .id(building.getId())
                .name(building.getName())
                .alias(building.getAlias())
                .type(building.getType())
                .latitude(building.getLatitude())
                .longitude(building.getLongitude())
                .classrooms(Optional.ofNullable(building.getClassrooms())
                        .orElse(Collections.emptySet())
                        .stream().map(classroom -> ClassroomDTO.builder()
                        .id(classroom.getId())
                        .name(classroom.getName())
                        .lectures(Optional.ofNullable(classroom.getLectures())
                                .orElse(Collections.emptySet())
                                .stream().map(lecture -> LectureDTO.builder()
                                .id(lecture.getId())
                                .dayOfWeek(lecture.getDay())
                                .period(lecture.getPeriod())
                                .module(lecture.getModule())
                                .build()).collect(Collectors.toSet()))
                        .build()).collect(Collectors.toSet()))
                .build();
    }
}

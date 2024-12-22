package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.request.BuildingRequestDTO;
import org.api.ufro_mapping.dto.request.update.BuildingUpdateDTO;
import org.api.ufro_mapping.dto.response.BuildingDTO;
import org.api.ufro_mapping.dto.response.LocationDTO;

import java.util.List;
import java.util.Optional;

public interface IBuildingService {
    List<BuildingDTO> findAll();
    Optional<BuildingDTO> findById(Long id);
    BuildingDTO save(BuildingRequestDTO buildingRequestDTO);
    Optional<BuildingDTO> update(Long id, BuildingUpdateDTO buildingRequestDTO);
    boolean delete(Long id);
    List<LocationDTO> findAllLocations();
}

package org.ufromap.feature.buildings.services;

import org.ufromap.feature.buildings.dto.EdificioRequestDTO;
import org.ufromap.feature.buildings.dto.EdificioDTO;
import org.ufromap.feature.buildings.dto.LocationDTO;
import org.ufromap.core.base.ICrudService;
import org.ufromap.feature.buildings.models.Edificio;

import java.util.List;

public interface IEdificioService extends ICrudService<EdificioDTO, EdificioRequestDTO, Edificio> {
    List<LocationDTO> findAllLocations();
}

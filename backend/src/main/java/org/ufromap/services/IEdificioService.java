package org.ufromap.services;

import org.ufromap.dto.request.EdificioRequestDTO;
import org.ufromap.dto.response.EdificioDTO;
import org.ufromap.dto.response.LocationDTO;
import org.ufromap.models.Edificio;

import java.util.List;

public interface IEdificioService extends ICrudService<EdificioDTO, EdificioRequestDTO, Edificio> {
    List<LocationDTO> findAllLocations();
}

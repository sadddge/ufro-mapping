package org.ufromap.feature.lectures.services;

import org.ufromap.core.base.ICrudService;
import org.ufromap.feature.buildings.dto.LocationDTO;
import org.ufromap.feature.lectures.dto.ClaseRequestDTO;
import org.ufromap.feature.lectures.dto.ClaseDTO;
import org.ufromap.feature.lectures.models.Clase;

public interface IClaseService extends ICrudService<ClaseDTO, ClaseRequestDTO, Clase> {
    boolean isInCurrentPeriod(int id);
    LocationDTO[] getNextLocations(int id);
}

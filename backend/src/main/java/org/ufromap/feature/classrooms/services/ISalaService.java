package org.ufromap.feature.classrooms.services;

import org.ufromap.core.base.ICrudService;
import org.ufromap.feature.classrooms.dto.SalaRequestDTO;
import org.ufromap.feature.classrooms.dto.SalaDTO;
import org.ufromap.core.exceptions.EntityNotFoundException;
import org.ufromap.feature.classrooms.models.Sala;

import java.util.List;

public interface ISalaService extends ICrudService<SalaDTO, SalaRequestDTO, Sala> {
    List<SalaDTO> getSalasByEdificioId(int edificioId) throws EntityNotFoundException;
}

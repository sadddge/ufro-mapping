package org.ufromap.services;

import org.ufromap.dto.request.SalaRequestDTO;
import org.ufromap.dto.response.SalaDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Sala;

import java.util.List;

public interface ISalaService extends ICrudService<SalaDTO, SalaRequestDTO, Sala> {
    List<SalaDTO> getSalasByEdificioId(int edificioId) throws EntityNotFoundException;
}

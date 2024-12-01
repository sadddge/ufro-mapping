package org.ufromap.services;

import org.ufromap.dto.request.AsignaturaRequestDTO;
import org.ufromap.dto.response.AsignaturaDTO;
import org.ufromap.dto.response.HorarioClaseDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;

public interface IAsignaturaService extends ICrudService<AsignaturaDTO, AsignaturaRequestDTO, Asignatura> {
}

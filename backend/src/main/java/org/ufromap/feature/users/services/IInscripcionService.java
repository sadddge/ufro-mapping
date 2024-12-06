package org.ufromap.feature.users.services;

import org.ufromap.core.base.ICrudService;
import org.ufromap.feature.users.dto.InscripcionRequestDTO;
import org.ufromap.feature.courses.dto.AsignaturaDTO;
import org.ufromap.feature.users.dto.InscripcionDTO;
import org.ufromap.feature.users.models.Inscripcion;

import java.util.List;

public interface IInscripcionService extends ICrudService<InscripcionDTO, InscripcionRequestDTO, Inscripcion> {
    List<AsignaturaDTO> getAsignaturasByUsuarioId(int id);
    void deleteByUsuarioIdAndAsignaturaId(int usuarioId, int asignaturaId);
}

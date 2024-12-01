package org.ufromap.services;

import org.ufromap.dto.request.InscripcionRequestDTO;
import org.ufromap.dto.response.AsignaturaDTO;
import org.ufromap.dto.response.InscripcionDTO;
import org.ufromap.models.Inscripcion;

import java.util.List;

public interface IInscripcionService extends ICrudService<InscripcionDTO, InscripcionRequestDTO, Inscripcion> {
    List<AsignaturaDTO> getAsignaturasByUsuarioId(int id);
    void deleteByUsuarioIdAndAsignaturaId(int usuarioId, int asignaturaId);
}

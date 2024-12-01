package org.ufromap.services;

import org.ufromap.dto.response.HorarioClaseDTO;

import java.util.List;

public interface IHorarioService {
    List<HorarioClaseDTO> getHorarioByUserId(int userId);
    List<HorarioClaseDTO> getHorarioBySalaId(int salaId);
    List<HorarioClaseDTO> getHorarioByAsignaturaId(int asignaturaId);
}

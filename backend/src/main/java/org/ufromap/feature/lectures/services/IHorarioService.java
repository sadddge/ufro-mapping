package org.ufromap.feature.lectures.services;

import org.ufromap.feature.lectures.dto.HorarioClaseDTO;

import java.util.List;

public interface IHorarioService {
    List<HorarioClaseDTO> getHorarioByUserId(int userId);
    List<HorarioClaseDTO> getHorarioBySalaId(int salaId);
    List<HorarioClaseDTO> getHorarioByAsignaturaId(int asignaturaId);
}

package org.ufromap.services.impl;

import org.ufromap.dto.response.HorarioClaseDTO;
import org.ufromap.repositories.ClaseRepository;
import org.ufromap.services.IHorarioService;

import java.util.List;

public class HorarioServiceImpl implements IHorarioService {
    private final ClaseRepository claseRepository;

    public HorarioServiceImpl() {
        this.claseRepository = new ClaseRepository();
    }
    public HorarioServiceImpl(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    @Override
    public List<HorarioClaseDTO> getHorarioByUserId(int userId) {
        return claseRepository.getHorarioByUserId(userId);
    }

    @Override
    public List<HorarioClaseDTO> getHorarioBySalaId(int salaId) {
        return claseRepository.getHorarioBySalaId(salaId);
    }

    @Override
    public List<HorarioClaseDTO> getHorarioByAsignaturaId(int asignaturaId) {
        return claseRepository.getHorarioByAsignaturaId(asignaturaId);
    }
}

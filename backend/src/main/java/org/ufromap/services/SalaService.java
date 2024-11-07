package org.ufromap.services;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Sala;
import org.ufromap.repositories.EdificioRepository;
import org.ufromap.repositories.SalaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaService {

    private final SalaRepository salaRepository;
    private final EdificioRepository edificioRepository;

    public SalaService() {
        this.salaRepository = new SalaRepository();
        this.edificioRepository = new EdificioRepository();
    }

    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala findById(int id) {
        Sala sala = salaRepository.findById(id);
        if (sala == null) {
            throw new EntityNotFoundException("No se encontró una sala con el ID proporcionado.");
        }
        return sala;
    }

    public List<Sala> findByFilter(Integer edificioId, String nombre) {
        Map<String, Object> filter = new HashMap<>();
        if (edificioId != null) filter.put("edificioId", edificioId);
        if (nombre != null) filter.put("nombre", nombre);
        List<Sala> salas = salaRepository.findByFilter(filter);
        if (salas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron salas con los filtros proporcionados.");
        }

        return salas;
    }

    public Sala add(Sala sala) {
        validateSala(sala);
        return salaRepository.add(sala);
    }

    public Sala update(Sala sala) {
        return salaRepository.update(updateSala(sala));
    }

    public boolean delete(int id) {
        findById(id);
        return salaRepository.delete(id);
    }

    public boolean deleteByEdificioId(int edificioId) {
        return salaRepository.deleteByEdificioId(edificioId);
    }

    private void validateSala(Sala sala) {
        if (sala.getNombre() == null || sala.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre de la sala es obligatorio.");
        }
        if (edificioRepository.findById(sala.getEdificioId()) == null) {
            throw new BadRequestException("El edificio de la sala no existe.");
        }
    }

    private Sala updateSala(Sala sala) {
        Sala existing = findById(sala.getId());
        if (sala.getNombre() == null) {
            sala.setNombre(existing.getNombre());
        }
        if (sala.getEdificioId() == -1) {
            sala.setEdificioId(existing.getEdificioId());
        }

        validateSala(sala);
        return sala;
    }


}

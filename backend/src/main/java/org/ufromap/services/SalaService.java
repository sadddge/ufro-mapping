package org.ufromap.services;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Sala;
import org.ufromap.repositories.EdificioRepository;
import org.ufromap.repositories.SalaRepository;

import java.util.List;
import java.util.Map;

public class SalaService implements IService<Sala> {

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
        if (sala == null) throw new EntityNotFoundException("No se encontró una sala con el ID proporcionado.");
        return sala;
    }

    public List<Sala> findByFilter(Map<String, Object> filters) {
        List<Sala> salas = salaRepository.findByFilter(filters);
        if (salas.isEmpty())
            throw new EntityNotFoundException("No se encontraron salas con los filtros proporcionados.");
        return salas;
    }

    public Sala add(Sala sala) {
        validateSala(sala);
        return salaRepository.add(sala);
    }

    public Sala update(Sala sala) {
        validateSala(sala);
        return salaRepository.update(sala);
    }

    public void delete(int id) {
        findById(id);
        salaRepository.delete(id);
    }

    private void validateSala(Sala sala) {
        if (sala.getNombre() == null || sala.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre de la sala es obligatorio.");
        }
        if (edificioRepository.findById(sala.getEdificioId()) == null) {
            throw new BadRequestException("El edificio de la sala no existe.");
        }
    }
}

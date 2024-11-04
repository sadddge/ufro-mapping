package org.ufromap.services;

import org.ufromap.models.Sala;
import org.ufromap.repositories.SalaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaService {

    private SalaRepository salaRepository;

    public SalaService() {
        this.salaRepository = new SalaRepository();
    }

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }
    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala findById(int id) {
        return salaRepository.findById(id);
    }

    public List<Sala> findByFilter(Integer edificioId, String nombre) {
        Map<String, Object> filter = new HashMap<>();
        if (edificioId != null) filter.put("edificio_id", edificioId);
        if (nombre != null) filter.put("nombre_sala", nombre);

        return salaRepository.findByFilter(filter);
    }

    public Sala add(Sala obj) {
        return salaRepository.add(obj);
    }

    public Sala update(Sala obj) {
        return salaRepository.update(obj);
    }

    public boolean delete(int id) {
        findById(id);
        return salaRepository.delete(id);
    }

    public boolean deleteByEdificioId(int edificioId) {
        return salaRepository.deleteByEdificioId(edificioId);
    }


}

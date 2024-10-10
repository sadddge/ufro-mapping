package org.ufromap.services;

import java.util.List;

import org.ufromap.models.Edificio;
import org.ufromap.repositories.EdificioRepository;

public class EdificioService {

    private EdificioRepository edificioRepository;

    public EdificioService() {
        this.edificioRepository = new EdificioRepository();
    }

    public EdificioService(EdificioRepository edificioRepository) {
        this.edificioRepository = edificioRepository;
    }

    public List<Edificio> getEdificios() {
        return edificioRepository.getEdificios();
    }

    public Edificio getEdificioById(int id) {
        return edificioRepository.getEdificioById(id);
    }

    public Edificio getEdificioByNombre(String nombre) {
        return edificioRepository.getEdificioByNombre(nombre);
    }

    public Edificio getEdificioByAlias(String alias) {
        return edificioRepository.getEdificioByAlias(alias);
    }

    public Edificio getEdificioByTipo(String tipo) {
        return edificioRepository.getEdificioByTipo(tipo);
    }

    public boolean addEdificio(Edificio edificio) {
        return edificioRepository.addEdificio(edificio);
    }

    public boolean updateEdificio(Edificio edificio) {
        return edificioRepository.updateEdificio(edificio);
    }

    public boolean deleteEdificio(int id) {
        return edificioRepository.deleteEdificio(id);
    }
    
}

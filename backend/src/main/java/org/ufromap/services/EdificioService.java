package org.ufromap.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Edificio;
import org.ufromap.repositories.EdificioRepository;

/**
 * Servicio que maneja la lógica de negocio para la entidad {@link Edificio}.
 * Se encarga de interactuar con el repositorio de edificios {@link EdificioRepository} 
 * y ofrece métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) 
 * sobre los edificios.
 */
public class EdificioService {

    private final EdificioRepository edificioRepository;
    private final SalaService salaService;

    /**
     * Constructor por defecto que inicializa el repositorio de edificios.
     */
    public EdificioService() {
        this.edificioRepository = new EdificioRepository();
        this.salaService = new SalaService();
    }

    /**
     * Constructor que permite inyectar una instancia de {@link EdificioRepository}.
     *
     * @param edificioRepository El repositorio de edificios a utilizar.
     */
    public EdificioService(EdificioRepository edificioRepository, SalaService salaService) {
        this.edificioRepository = edificioRepository;
        this.salaService = salaService;
    }

    /**
     * Obtiene todos los edificios registrados en la base de datos.
     *
     * @return Una lista de objetos {@link Edificio} que contiene todos los edificios.
     */
    public List<Edificio> findAll() {
        return edificioRepository.findAll();
    }

    /**
     * Obtiene un edificio específico por su ID.
     *
     * @param id El ID del edificio a buscar.
     * @return El objeto {@link Edificio} correspondiente al ID proporcionado, o {@code null} si no se encuentra.
     */
    public Edificio findById(int id) {
        Edificio edificio = edificioRepository.findById(id);
        if (edificio == null) {
            throw new EntityNotFoundException("No se encontró un edificio con el ID proporcionado.");
        }
        return edificio;
    }


    /**
     * Obtiene una lista de edificios filtrados por los parámetros proporcionados.
     *
     * @param nombre   El nombre del edificio a buscar.
     * @param alias    El alias del edificio a buscar.
     * @param tipo     El tipo de edificio a buscar.
     * @param latitud  La latitud del edificio a buscar.
     * @param longitud La longitud del edificio a buscar.
     * @return Una lista de objetos {@link Edificio} que coinciden con los parámetros de búsqueda.
     */
    public List<Edificio> findByFilter(String nombre, String alias, String tipo, Float latitud, Float longitud) {
        Map<String, Object> filters = new HashMap<>();
        if (nombre != null) filters.put("nombre", nombre);
        if (alias != null) filters.put("alias", alias);
        if (tipo != null) filters.put("tipo", tipo);
        if (latitud != null) filters.put("latitud", latitud);
        if (longitud != null) filters.put("longitud", longitud);
        List<Edificio> edificios = edificioRepository.findByFilter(filters);
        if (edificios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron edificios con los filtros proporcionados.");
        }
        return edificios;
    }


    /**
     * Agrega un nuevo edificio a la base de datos.
     *
     * @param edificio El objeto {@link Edificio} con los datos del nuevo edificio.
     */
    public Edificio add(Edificio edificio) {
        validateEdificio(edificio);
         return edificioRepository.add(edificio);
    }

    /**
     * Actualiza los datos de un edificio existente.
     *
     * @param edificio El objeto {@link Edificio} con los datos actualizados.
     */
    public Edificio update(Edificio edificio) {
        validateEdificio(edificio);
        return edificioRepository.update(updateEdificio(edificio));
    }

    /**
     * Elimina un edificio de la base de datos por su ID.
     *
     * @param id El ID del edificio a eliminar.
     */
    public boolean delete(int id) {
        findById(id);
        boolean deleteSalas = salaService.deleteByEdificioId(id);
        return edificioRepository.delete(id) && deleteSalas;
    }



    private void validateEdificio(Edificio edificio) {
        if (edificio.getNombre() == null || edificio.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del edificio es obligatorio.");
        }
        if (edificio.getLatitud() == null) {
            throw new BadRequestException("La latitud del edificio es obligatoria.");
        }
        if (edificio.getLongitud() == null) {
            throw new BadRequestException("La longitud del edificio es obligatoria.");
        }
    }

    private Edificio updateEdificio(Edificio edificio) {
        Edificio edificioExistente = findById(edificio.getId());
        String alias = edificio.getAlias() == null ? edificioExistente.getAlias() : edificio.getAlias();
        String tipo = edificio.getTipo() == null ? edificioExistente.getTipo() : edificio.getTipo();
        return new Edificio(edificio.getId(), edificio.getNombre(), alias, tipo, edificio.getLatitud(), edificio.getLongitud(), edificio.getSalas());
    }
}

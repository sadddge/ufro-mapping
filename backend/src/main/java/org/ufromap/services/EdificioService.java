package org.ufromap.services;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
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
public class EdificioService implements IService<Edificio> {

    private final EdificioRepository edificioRepository;

    /**
     * Constructor por defecto que inicializa el repositorio de edificios.
     */
    public EdificioService() {
        this.edificioRepository = new EdificioRepository();
    }

    /**
     * Obtiene todos los edificios registrados en la base de datos.
     *
     * @return Una lista de objetos {@link Edificio} que contiene todos los edificios.
     */
    @Override
    public List<Edificio> findAll() {
        return edificioRepository.findAll();
    }

    /**
     * Obtiene un edificio específico por su ID.
     *
     * @param id El ID del edificio a buscar.
     * @return El objeto {@link Edificio} correspondiente al ID proporcionado, o {@code null} si no se encuentra.
     */
    @Override
    public Edificio findById(int id) {
        Edificio edificio = edificioRepository.findById(id);
        if (edificio == null) throw new EntityNotFoundException("No se encontró un edificio con el ID proporcionado.");
        return edificio;
    }

    @Override
    public List<Edificio> findByFilter(Map<String, Object> filters) {
        List<Edificio> edificios = edificioRepository.findByFilter(filters);
        if (edificios.isEmpty())
            throw new EntityNotFoundException("No se encontraron edificios con los filtros proporcionados.");
        return edificios;
    }


    /**
     * Agrega un nuevo edificio a la base de datos.
     *
     * @param edificio El objeto {@link Edificio} con los datos del nuevo edificio.
     */
    @Override
    public Edificio add(Edificio edificio) {
        validateEdificio(edificio);
        return edificioRepository.add(edificio);
    }

    /**
     * Actualiza los datos de un edificio existente.
     *
     * @param edificio El objeto {@link Edificio} con los datos actualizados.
     */
    @Override
    public Edificio update(Edificio edificio) {
        validateEdificio(edificio);
        return edificioRepository.update(edificio);
    }

    @Override
    public Edificio patch(Edificio entity, JSONObject jsonObject) throws EntityNotFoundException {
        Edificio edificio = new Edificio();
        String nombre = jsonObject.optString("nombre", entity.getNombre());
        String alias = jsonObject.optString("alias", entity.getAlias());
        String tipo = jsonObject.optString("tipo", entity.getTipo());
        float latitud = jsonObject.optFloat("latitud", entity.getLatitud());
        float longitud = jsonObject.optFloat("longitud", entity.getLongitud());
        edificio.setId(entity.getId());
        edificio.setNombre(nombre);
        edificio.setAlias(alias);
        edificio.setTipo(tipo);
        edificio.setLatitud(latitud);
        edificio.setLongitud(longitud);
        return update(edificio);
    }


    /**
     * Elimina un edificio de la base de datos por su ID.
     *
     * @param id El ID del edificio a eliminar.
     */
    @Override
    public void delete(int id) {
        findById(id);
        edificioRepository.delete(id);
    }

    private void validateEdificio(Edificio edificio) {
        if (edificio.getNombre() == null || edificio.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del edificio es obligatorio.");
        }
        if (edificio.getLatitud() == -1) {
            throw new BadRequestException("La latitud del edificio es obligatoria.");
        }
        if (edificio.getLongitud() == -1) {
            throw new BadRequestException("La longitud del edificio es obligatoria.");
        }
    }
}

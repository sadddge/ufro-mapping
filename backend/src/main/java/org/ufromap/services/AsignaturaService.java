package org.ufromap.services;


import java.sql.SQLException;

import org.ufromap.models.Asignatura;
import org.ufromap.repositories.AsignaturaRepository;

/**
 * Servicio que maneja la lógica de negocio relacionada con la entidad "Asignatura".
 * Utiliza el repositorio de Asignatura para realizar las operaciones CRUD y otras funcionalidades.
 */
public class AsignaturaService {

    private AsignaturaRepository asignaturaRepository;

    /**
     * Constructor que inicializa el servicio con un repositorio de Asignatura.
     * @param asignaturaRepository el repositorio para realizar las operaciones de base de datos sobre Asignatura.
     */
    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    /**
     * Método para registrar una nueva asignatura. (Lógica de negocio aún no implementada).
     * @param asignatura el objeto Asignatura que se desea registrar.
     */
    public void registrarAsignatura(Asignatura asignatura) {

    }

    /**
     * Recupera una asignatura desde el repositorio basándose en su código.
     * @param codigo el código único de la asignatura.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     */
    public Asignatura getAsignaturaByCodigo( String codigo) {
        return asignaturaRepository.getAsignaturaByCodigo(codigo);
    }

    /**
     * Recupera una asignatura desde el repositorio basándose en su nombre.
     * @param nombre el nombre de la asignatura.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     */
    public Asignatura getAsignaturaByNombre( String nombre) {
        return asignaturaRepository.getAsignaturaByNombre(nombre);
    }

    /**
     * Recupera una asignatura desde el repositorio basándose en su ID.
     * @param id el ID de la asignatura.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre algún error durante la ejecución de la consulta SQL.
     */
    public Asignatura getAsignaturaById(int id) throws SQLException {
        return asignaturaRepository.getAsignaturaById(id);
    }

    /**
     * Agrega una nueva asignatura utilizando el repositorio.
     * @param asignatura el nombre de la asignatura.
     * @param codigo el código de la asignatura.
     * @param descripcion una breve descripción de la asignatura.
     */
    public void addAsignatura(String asignatura, String codigo, String descripcion) {
        asignaturaRepository.addAsignatura(asignatura, codigo, descripcion);
    }

    /**
     * Actualiza una asignatura existente en el repositorio.
     * @param asignatura el nuevo nombre de la asignatura.
     * @param codigo el nuevo código de la asignatura.
     * @param descripcion la nueva descripción de la asignatura.
     * @param asignatura_id el ID de la asignatura a actualizar.
     */
    public void updateAsignatura(String asignatura, String codigo, String descripcion, int asignatura_id) {
        asignaturaRepository.updateAsignatura(asignatura, codigo, descripcion, asignatura_id);
    }

    /**
     * Elimina una asignatura del repositorio basándose en su ID.
     * @param asignatura_id el ID de la asignatura a eliminar.
     */
    public void deleteAsignatura(int asignatura_id) {
        asignaturaRepository.deleteAsignatura(asignatura_id);
    }


}
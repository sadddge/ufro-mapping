package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Edificio;

/**
 * Repositorio para manejar las operaciones relacionadas con la entidad {@link Edificio}.
 * Provee métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la tabla 'edificio'.
 */
public class EdificioRepository implements IRepository {

    private final SalaRepository salaRepository;

    /**
     * Constructor por defecto que inicializa una instancia de {@link SalaRepository}.
     */
    public EdificioRepository() {
        this.salaRepository = new SalaRepository();
    }

    /**
     * Constructor que permite inyectar una instancia de {@link SalaRepository}.
     *
     * @param salaRepository Instancia del repositorio de salas.
     */
    public EdificioRepository(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    /**
     * Obtiene todos los edificios registrados en la base de datos.
     *
     * @return Una lista de objetos {@link Edificio} con los detalles de cada edificio y sus salas.
     */
    @Override
    public List<Edificio> findAll() {
        List<Edificio> edificios = new ArrayList<>();
        String query = "SELECT edificio_id, nombre_edificio, alias_edificio, tipo_edificio, latitud, longitud FROM edificio";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                edificios.add((Edificio) mapToObject(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return edificios;
    }

    /**
     * Obtiene un edificio específico por su ID.
     *
     * @param id El ID del edificio a buscar.
     * @return Un objeto {@link Edificio} con los detalles del edificio, o {@code null} si no se encuentra.
     */
    @Override
    public Edificio findById(int id) {
        Edificio edificio = null;
        String query = "SELECT nombre_edificio, alias_edificio, tipo_edificio, latitud, longitud FROM edificio WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    edificio = (Edificio) mapToObject(resultSet);
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
        return edificio;
    }

    /**
     * Busca edificios en la base de datos que coincidan con los filtros proporcionados.
     *
     * @param filters Un mapa con los filtros a aplicar en la búsqueda.
     * @return Una lista de objetos {@link Edificio} que coinciden con los filtros proporcionados.
     */
    @Override
    public List<Edificio> findByFilter(Map<String, Object> filters) {
        List<Edificio> edificios = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM edificio WHERE 1=1");

        if (filters.containsKey("nombre")) query.append(" AND nombre_edificio = ?");
        if (filters.containsKey("alias")) query.append(" AND alias_edificio = ?");
        if (filters.containsKey("tipo")) query.append(" AND tipo_edificio = ?");
        if (filters.containsKey("latitud")) query.append(" AND latitud = ?");
        if (filters.containsKey("longitud")) query.append(" AND longitud = ?");

        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;

            if (filters.containsKey("nombre")) statement.setString(index++, (String) filters.get("nombre"));
            if (filters.containsKey("alias")) statement.setString(index++, (String) filters.get("alias"));
            if (filters.containsKey("tipo")) statement.setString(index++, (String) filters.get("tipo"));
            if (filters.containsKey("latitud")) statement.setFloat(index++, (Float) filters.get("latitud"));
            if (filters.containsKey("longitud")) statement.setFloat(index, (Float) filters.get("longitud"));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    edificios.add((Edificio) mapToObject(resultSet));
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }

        return edificios;
    }

    /**
     * Agrega un nuevo edificio a la base de datos.
     *
     * @param obj El objeto {@link Edificio} con los datos del nuevo edificio.
     */
    @Override
    public void add(Object obj) {
        String query = "INSERT INTO edificio (nombre_edificio, alias_edificio, tipo_edificio, latitud, longitud) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            Edificio edificio = (Edificio) obj;
            statement.setString(1, edificio.getNombre());
            statement.setString(2, edificio.getAlias());
            statement.setString(3, edificio.getTipo());
            statement.setFloat(4, edificio.getLatitud());
            statement.setFloat(5, edificio.getLongitud());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
    }

    /**
     * Actualiza un edificio en la base de datos.
     *
     * @param obj El objeto {@link Edificio} con los datos actualizados del edificio.
     */
    @Override
    public void update(Object obj) {
        String query = "UPDATE edificio SET nombre_edificio = ?, alias_edificio = ?, tipo_edificio = ?, latitud = ?, longitud = ? WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            Edificio edificio = (Edificio) obj;
            statement.setString(1, edificio.getNombre());
            statement.setString(2, edificio.getAlias());
            statement.setString(3, edificio.getTipo());
            statement.setFloat(4, edificio.getLatitud());
            statement.setFloat(5, edificio.getLongitud());
            statement.setInt(6, edificio.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
    }

    /**
     * Elimina un edificio de la base de datos por su ID.
     *
     * @param id El ID del edificio a eliminar.
     */
    @Override
    public void delete(int id) {
        String query = "DELETE FROM edificio WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
    }

    /**
     * Mapea un objeto {@link ResultSet} a un objeto {@link Edificio}.
     *
     * @param resultSet El objeto {@link ResultSet} que contiene los datos del edificio.
     * @return Un objeto {@link Edificio} con los datos mapeados.
     */
    @Override
    public Object mapToObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("edificio_id");
            String nombre = resultSet.getString("nombre_edificio");
            String alias = resultSet.getString("alias_edificio");
            String tipo = resultSet.getString("tipo_edificio");
            float latitud = resultSet.getFloat("latitud");
            float longitud = resultSet.getFloat("longitud");

            return new Edificio(id, nombre, alias, tipo, latitud, longitud, new ArrayList<>());
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
            return null;
        }
    }
}

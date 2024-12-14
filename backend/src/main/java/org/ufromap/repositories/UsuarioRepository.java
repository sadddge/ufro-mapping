package org.ufromap.repositories;

import lombok.extern.java.Log;
import org.ufromap.core.base.BaseRepository;
import org.ufromap.core.exceptions.InternalErrorException;
import org.ufromap.feature.users.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;

@Log
public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super();
    }

    public UsuarioRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "usuario";
    }

    @Override
    protected String getColumns() {
        return "id, nombre_rol, nombre_usuario, correo, contrasenia";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO usuario (nombre_rol, nombre_usuario, correo, contrasenia) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE usuario SET nombre_usuario = ?, correo = ?, contrasenia = ? WHERE id = ?";
    }

    @Override
    protected Usuario mapToObject(ResultSet resultSet) {
        Usuario usuario = new Usuario();
        try {
            usuario.setId(resultSet.getInt("id"));
            usuario.setRol(resultSet.getString("nombre_rol"));
            usuario.setNombre(resultSet.getString("nombre_usuario"));
            usuario.setCorreo(resultSet.getString("correo"));
            usuario.setContrasenia(resultSet.getString("contrasenia"));
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error mapping object", e);
            throw new InternalErrorException("Error mapping object");
        }
        return usuario;
    }

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Usuario obj) throws SQLException {
        statement.setString(1, obj.getRol());
        statement.setString(2, obj.getNombre());
        statement.setString(3, obj.getCorreo());
        statement.setString(4, obj.getContrasenia());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Usuario obj) throws SQLException {
        statement.setString(1, obj.getNombre());
        statement.setString(2, obj.getCorreo());
        statement.setString(3, obj.getContrasenia());
        statement.setInt(4, obj.getId());
    }

    public Optional<Usuario> findByCorreoYContrasenia(String correo, String contrasenia) {
        String query = "SELECT id, nombre_rol, nombre_usuario, correo, contrasenia FROM usuario WHERE correo = ? AND contrasenia = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, correo);
            statement.setString(2, contrasenia);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToObject(resultSet));
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new InternalErrorException("Error finding object");
        }
        return Optional.empty();
    }

    public Optional<Object> findByCorreo(String correo) {
        String query = "SELECT id, nombre_rol, nombre_usuario, correo, contrasenia FROM usuario WHERE correo = ?";
        return getObject(correo, query);
    }

    public Optional<Object> findByNombre(String nombre) {
        String query = "SELECT id, nombre_rol, nombre_usuario, correo, contrasenia FROM usuario WHERE nombre_usuario = ?";
        return getObject(nombre, query);
    }

    private Optional<Object> getObject(String nombre, String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToObject(resultSet));
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new InternalErrorException("Error finding object");
        }
        return Optional.empty();
    }
}

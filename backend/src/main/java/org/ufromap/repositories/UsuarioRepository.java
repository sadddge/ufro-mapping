package org.ufromap.repositories;

import lombok.extern.java.Log;
import org.ufromap.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.Set;

@Log
public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super();
    }

    public UsuarioRepository(Connection connection, InscripcionRepository inscripcionRepository) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "usuario";
    }

    @Override
    protected String getColumns() {
        return "id, nombre_usuario, correo, contrasenia";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO usuario (nombre_usuario, correo, contrasenia) VALUES (?, ?, ?)";
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
            usuario.setNombre(resultSet.getString("nombre_usuario"));
            usuario.setCorreo(resultSet.getString("correo"));
            usuario.setContrasenia(resultSet.getString("contrasenia"));
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error mapping object", e);
        }
        return usuario;
    }

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Usuario obj) throws SQLException {
        statement.setString(1, obj.getNombre());
        statement.setString(2, obj.getCorreo());
        statement.setString(3, obj.getContrasenia());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Usuario obj) throws SQLException {
        statement.setString(1, obj.getNombre());
        statement.setString(2, obj.getCorreo());
        statement.setString(3, obj.getContrasenia());
        statement.setInt(4, obj.getId());
    }

    public Usuario findByCorreoYContrasenia(String correo, String contrasenia) {
        String query = "SELECT id, nombre_usuario, correo, contrasenia FROM usuario WHERE correo = ?AND contrasenia = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, correo);
            statement.setString(2, contrasenia);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToObject(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}

package org.ufromap.repositories;

import org.ufromap.models.Asignatura;
import org.ufromap.models.Usuario;
import org.ufromap.services.AsignaturaService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UsuarioRepository extends BaseRepository<Usuario> {
    private final InscripcionRepository inscripcionRepository;
    private final AsignaturaService asignaturaService;

    public UsuarioRepository() {
        super();
        this.inscripcionRepository = new InscripcionRepository();
        this.asignaturaService = new AsignaturaService();
    }

    public UsuarioRepository(Connection connection, InscripcionRepository inscripcionRepository, AsignaturaService asignaturaService) {
        super(connection);
        this.inscripcionRepository = inscripcionRepository;
        this.asignaturaService = asignaturaService;
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
        try {
            int id = resultSet.getInt("id");
            String nombreUsuario = resultSet.getString("nombre_usuario");
            String correo = resultSet.getString("correo");
            String contrasenia = resultSet.getString("contrasenia");
            Set<Asignatura> asignaturas = asignaturaService.getAsignaturasByInscripciones(inscripcionRepository.getInscripcionByUsuarioId(id));
            return new Usuario(id, nombreUsuario, correo, contrasenia, asignaturas);
        } catch (SQLException e) {
            return null;
        }
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

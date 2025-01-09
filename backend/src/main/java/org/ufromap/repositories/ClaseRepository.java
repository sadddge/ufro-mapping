package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;
import org.ufromap.core.base.BaseRepository;
import org.ufromap.core.exceptions.InternalErrorException;
import org.ufromap.feature.lectures.dto.HorarioClaseDTO;
import org.ufromap.feature.lectures.models.Clase;

@Log
public class ClaseRepository extends BaseRepository<Clase> {

    public ClaseRepository() {
        super();
    }

    public ClaseRepository(Connection connection) {
        super(connection);
    }

    public List<HorarioClaseDTO> getHorarioByUserId(int userId){
        String query = "SELECT c.id, a.nombre_asignatura, a.codigo, c.modulo, s.nombre_sala, c.dia_semana, c.periodo FROM clase c " +
                "JOIN asignatura a ON c.asignatura_id = a.id " +
                "JOIN sala s ON c.sala_id = s.id " +
                "JOIN inscribe i ON c.asignatura_id = i.asignatura_id " +
                "WHERE i.usuario_id = ?";
        return getHorariosByQuery(query, userId);
    }

    public List<HorarioClaseDTO> getHorarioBySalaId(int salaId) {
        String query = "SELECT c.id, a.nombre_asignatura, a.codigo, c.modulo, s.nombre_sala, c.dia_semana, c.periodo FROM clase c " +
                "JOIN asignatura a ON c.asignatura_id = a.id " +
                "JOIN sala s ON c.sala_id = s.id " +
                "WHERE s.id = ?";
        return getHorariosByQuery(query, salaId);
    }

    public List<HorarioClaseDTO> getHorarioByAsignaturaId(int asignaturaId) {
        String query = "SELECT c.id, a.nombre_asignatura, a.codigo, c.modulo, s.nombre_sala, c.dia_semana, c.periodo FROM clase c " +
                "JOIN asignatura a ON c.asignatura_id = a.id " +
                "JOIN sala s ON c.sala_id = s.id " +
                "WHERE a.id = ?";
        return getHorariosByQuery(query, asignaturaId);
    }

    private List<HorarioClaseDTO> getHorariosByQuery(String query, int id) {
        List<HorarioClaseDTO> horarios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    HorarioClaseDTO horario = new HorarioClaseDTO();
                    horario.setIdClase(resultSet.getInt("id"));
                    horario.setNombreAsignatura(resultSet.getString("nombre_asignatura"));
                    horario.setCodigoAsignatura(resultSet.getString("codigo"));
                    horario.setModulo(resultSet.getInt("modulo"));
                    horario.setNombreSala(resultSet.getString("nombre_sala"));
                    horario.setDiaSemana(resultSet.getInt("dia_semana"));
                    horario.setPeriodo(resultSet.getInt("periodo"));
                    horarios.add(horario);
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Error executing query: " + query, e);
            throw new InternalErrorException("Failed to execute insert operation");
        }
        return horarios;
    }

    @Override
    protected String getTableName() {
        return "clase";
    }

    @Override
    protected String getColumns() {
        return "id, sala_id, asignatura_id, dia_semana, periodo, docente_nombre, modulo";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO clase (sala_id, asignatura_id, dia_semana, periodo, docente_nombre, modulo) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE clase SET sala_id = ?, asignatura_id = ?, dia_semana = ?, periodo = ?, docente_nombre = ?, modulo = ? WHERE id = ?";
    }

    @Override
    public Clase mapToObject(ResultSet resultSet) {
        Clase clase = new Clase();
        try {
            clase.setId(resultSet.getInt("id"));
            clase.setSalaId(resultSet.getInt("sala_id"));
            clase.setAsignaturaId(resultSet.getInt("asignatura_id"));
            clase.setDiaSemana(resultSet.getInt("dia_semana"));
            clase.setPeriodo(resultSet.getInt("periodo"));
            clase.setDocente(resultSet.getString("docente_nombre"));
            clase.setModulo(resultSet.getInt("modulo"));
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Error mapping object", e);
            throw new InternalErrorException("Failed to map object");
        }
        return clase;
    }

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Clase obj) throws SQLException {
        statement.setInt(1, obj.getSalaId());
        statement.setInt(2, obj.getAsignaturaId());
        statement.setInt(3, obj.getDiaSemana());
        statement.setInt(4, obj.getPeriodo());
        statement.setString(5, obj.getDocente());
        statement.setInt(6, obj.getModulo());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Clase obj) throws SQLException {
        setParametersForInsert(statement, obj);
        statement.setInt(7, obj.getId());
    }

    public List<Clase> getClasesByUserId(int userId) {
        List<Clase> clases = new ArrayList<>();
        String query = "SELECT c.id, c.sala_id, c.asignatura_id, c.dia_semana, c.periodo, c.docente_nombre, c.modulo FROM clase c " +
                "JOIN inscribe i ON c.asignatura_id = i.asignatura_id " +
                "WHERE i.usuario_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clases.add(mapToObject(resultSet));
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Error executing query: " + query, e);
            throw new InternalErrorException("Failed to execute insert operation");
        }
        return clases;
    }

}
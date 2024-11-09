package org.ufromap.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.ufromap.models.Clase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ClaseRepositoryTest {

    private ClaseRepository claseRepository;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        claseRepository = new ClaseRepository();
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        // Simula la conexión a la base de datos
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
    }

    @Test
    public void testGetClases() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("sala_id")).thenReturn(101);
        when(mockResultSet.getInt("edificio_id")).thenReturn(201);
        when(mockResultSet.getInt("asignatura_id")).thenReturn(301);
        when(mockResultSet.getInt("dia_semana")).thenReturn(1);
        when(mockResultSet.getInt("periodo")).thenReturn(2023);
        when(mockResultSet.getString("docente_nombre")).thenReturn("Profesor Test");
        when(mockResultSet.getInt("modulo")).thenReturn(1);

        List<Clase> clases = claseRepository.getClases();
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClaseById() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("sala_id")).thenReturn(101);
        when(mockResultSet.getInt("edificio_id")).thenReturn(201);
        when(mockResultSet.getInt("asignatura_id")).thenReturn(301);
        when(mockResultSet.getInt("dia_semana")).thenReturn(2);
        when(mockResultSet.getInt("periodo")).thenReturn(2023);
        when(mockResultSet.getString("docente_nombre")).thenReturn("Profesor Test");
        when(mockResultSet.getInt("modulo")).thenReturn(1);

        Clase clase = claseRepository.getClaseById(1);
        assertNotNull(clase);
        assertEquals("Profesor Test", clase.getDocenteNombre());
        assertEquals(101, clase.getSalaId());
    }

    @Test
    public void testGetClasesBySalaId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("edificio_id")).thenReturn(201);
        when(mockResultSet.getInt("asignatura_id")).thenReturn(301);
        when(mockResultSet.getInt("dia_semana")).thenReturn(3);
        when(mockResultSet.getInt("periodo")).thenReturn(2023);
        when(mockResultSet.getString("docente_nombre")).thenReturn("Profesor Test");
        when(mockResultSet.getInt("modulo")).thenReturn(2);

        List<Clase> clases = claseRepository.getClasesBySalaId(101);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClasesByEdificioId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("sala_id")).thenReturn(101);
        when(mockResultSet.getInt("asignatura_id")).thenReturn(301);
        when(mockResultSet.getInt("dia_semana")).thenReturn(4);
        when(mockResultSet.getInt("periodo")).thenReturn(2023);
        when(mockResultSet.getString("docente_nombre")).thenReturn("Profesor Test");
        when(mockResultSet.getInt("modulo")).thenReturn(3);

        List<Clase> clases = claseRepository.getClasesByEdificioId(201);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClasesByAsignaturaId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("sala_id")).thenReturn(101);
        when(mockResultSet.getInt("edificio_id")).thenReturn(201);
        when(mockResultSet.getInt("dia_semana")).thenReturn(5);
        when(mockResultSet.getInt("periodo")).thenReturn(2023);
        when(mockResultSet.getString("docente_nombre")).thenReturn("Profesor Test");
        when(mockResultSet.getInt("modulo")).thenReturn(4);

        List<Clase> clases = claseRepository.getClasesByAsignaturaId(301);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testGetClasesByDiaSemana() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("sala_id")).thenReturn(101);
        when(mockResultSet.getInt("edificio_id")).thenReturn(201);
        when(mockResultSet.getInt("asignatura_id")).thenReturn(301);
        when(mockResultSet.getInt("periodo")).thenReturn(2023);
        when(mockResultSet.getString("docente_nombre")).thenReturn("Profesor Test");
        when(mockResultSet.getInt("modulo")).thenReturn(1);

        List<Clase> clases = claseRepository.getClasesByDiaSemana(1);
        assertNotNull(clases);
        assertEquals(1, clases.size());
        assertEquals("Profesor Test", clases.get(0).getDocenteNombre());
    }

    @Test
    public void testAddClase() throws SQLException {
        Clase clase = new Clase(1, 101, 201, 301, 1, 2023, "Profesor Test", 1);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = claseRepository.addClase(clase);
        assertTrue(result);
    }

    @Test
    public void testUpdateClase() throws SQLException {
        Clase clase = new Clase(1, 101, 201, 301, 2, 2023, "Profesor Actualizado", 1);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = claseRepository.updateClase(clase);
        assertTrue(result);
    }

    @Test
    public void testDeleteClase() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = claseRepository.deleteClase(1);
        assertTrue(result);
    }
}

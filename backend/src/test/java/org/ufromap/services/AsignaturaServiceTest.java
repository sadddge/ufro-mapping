package org.ufromap.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ufromap.models.Asignatura;
import org.ufromap.repositories.AsignaturaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private AsignaturaService asignaturaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarAsignatura() {
        Asignatura asignatura = new Asignatura(1, "Matemáticas", "MAT101", "Curso básico de Matemáticas", 5);
        asignaturaService.registrarAsignatura(asignatura);
        // Verifica que no lanza excepciones ni realiza acciones (actualiza cuando haya implementación)
        assertDoesNotThrow(() -> asignaturaService.registrarAsignatura(asignatura));
    }

    @Test
    void testGetAsignaturaByCodigo() {
        String codigo = "MAT101";
        Asignatura asignaturaMock = new Asignatura(1, "Matemáticas", "MAT101", "Curso básico de Matemáticas", 5);
        when(asignaturaRepository.getAsignaturaByCodigo(codigo)).thenReturn(asignaturaMock);

        Asignatura result = asignaturaService.getAsignaturaByCodigo(codigo);

        assertNotNull(result);
        assertEquals(asignaturaMock, result);
        verify(asignaturaRepository, times(1)).getAsignaturaByCodigo(codigo);
    }

    @Test
    void testGetAsignaturaByNombre() {
        String nombre = "Matemáticas";
        Asignatura asignaturaMock = new Asignatura(1, "Matemáticas", "MAT101", "Curso básico de Matemáticas", 5);
        when(asignaturaRepository.getAsignaturaByNombre(nombre)).thenReturn(asignaturaMock);

        Asignatura result = asignaturaService.getAsignaturaByNombre(nombre);

        assertNotNull(result);
        assertEquals(asignaturaMock, result);
        verify(asignaturaRepository, times(1)).getAsignaturaByNombre(nombre);
    }

    @Test
    void testGetAsignaturaById() throws SQLException {
        int id = 1;
        Asignatura asignaturaMock = new Asignatura(1, "Matemáticas", "MAT101", "Curso básico de Matemáticas", 5);
        when(asignaturaRepository.getAsignaturaById(id)).thenReturn(asignaturaMock);

        Asignatura result = asignaturaService.getAsignaturaById(id);

        assertNotNull(result);
        assertEquals(asignaturaMock, result);
        verify(asignaturaRepository, times(1)).getAsignaturaById(id);
    }

    @Test
    void testGetAsignaturaByIdThrowsSQLException() throws SQLException {
        int id = 1;
        when(asignaturaRepository.getAsignaturaById(id)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> asignaturaService.getAsignaturaById(id));
        verify(asignaturaRepository, times(1)).getAsignaturaById(id);
    }

    @Test
    void testAddAsignatura() {
        Asignatura asignatura = new Asignatura(1, "Física", "PHY101", "Curso de Física General", 6);

        asignaturaService.addAsignatura(asignatura);

        verify(asignaturaRepository, times(1)).addAsignatura(asignatura);
    }

    @Test
    void testUpdateAsignatura() {
        Asignatura asignatura = new Asignatura(1, "Física", "PHY101", "Curso de Física General", 6);

        asignaturaService.updateAsignatura(asignatura);

        verify(asignaturaRepository, times(1)).updateAsignatura(asignatura);
    }

    @Test
    void testDeleteAsignatura() {
        int asignaturaId = 1;

        asignaturaService.deleteAsignatura(asignaturaId);

        verify(asignaturaRepository, times(1)).deleteAsignatura(asignaturaId);
    }

    @Test
    void testGetAllAsignaturas() {
        List<Asignatura> asignaturasMock = new ArrayList<>();
        asignaturasMock.add(new Asignatura(1, "Matemáticas", "MAT101", "Curso básico de Matemáticas", 5));
        asignaturasMock.add(new Asignatura(2, "Física", "PHY101", "Curso de Física General", 6));

        when(asignaturaRepository.getAsignaturas()).thenReturn(asignaturasMock);

        List<Asignatura> result = asignaturaRepository.getAsignaturas();

        assertNotNull(result);
        assertEquals(asignaturasMock.size(), result.size());
        verify(asignaturaRepository, times(1)).getAsignaturas();
    }
}

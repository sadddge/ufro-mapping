package org.ufromap.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ufromap.models.Clase;
import org.ufromap.repositories.ClaseRepository;

import java.util.ArrayList;
import java.util.List;

class ClaseServiceTest {

    @Mock
    private ClaseRepository claseRepository;

    @InjectMocks
    private ClaseService claseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClases() {
        List<Clase> clasesMock = new ArrayList<>();
        clasesMock.add(new Clase(1, 101, 1, 1, 2, 1, "Profesor X", 1));
        clasesMock.add(new Clase(2, 102, 2, 1, 3, 2, "Profesor Y", 2));

        when(claseRepository.getClases()).thenReturn(clasesMock);

        claseService.getAllClases();

        verify(claseRepository, times(1)).getClases();
    }

    @Test
    void testGetClaseById() {
        int id = 1;
        Clase claseMock = new Clase(id, 101, 1, 1, 2, 1, "Profesor X", 1);
        when(claseRepository.getClaseById(id)).thenReturn(claseMock);

        Clase result = claseService.getClaseById(id);

        assertNotNull(result);
        assertEquals(claseMock, result);
        verify(claseRepository, times(1)).getClaseById(id);
    }

    @Test
    void testGetClasesBySalaId() {
        int salaId = 101;
        List<Clase> clasesMock = new ArrayList<>();
        clasesMock.add(new Clase(1, salaId, 1, 1, 2, 1, "Profesor X", 1));

        when(claseRepository.getClasesBySalaId(salaId)).thenReturn(clasesMock);

        List<Clase> result = claseService.getClasesBySalaId(salaId);

        assertNotNull(result);
        assertEquals(clasesMock, result);
        verify(claseRepository, times(1)).getClasesBySalaId(salaId);
    }

    @Test
    void testGetClasesByEdificioId() {
        int edificioId = 1;
        List<Clase> clasesMock = new ArrayList<>();
        clasesMock.add(new Clase(1, 101, edificioId, 1, 2, 1, "Profesor X", 1));

        when(claseRepository.getClasesByEdificioId(edificioId)).thenReturn(clasesMock);

        List<Clase> result = claseService.getClasesByEdificioId(edificioId);

        assertNotNull(result);
        assertEquals(clasesMock, result);
        verify(claseRepository, times(1)).getClasesByEdificioId(edificioId);
    }

    @Test
    void testGetClasesByAsignaturaId() {
        int asignaturaId = 1;
        List<Clase> clasesMock = new ArrayList<>();
        clasesMock.add(new Clase(1, 101, 1, asignaturaId, 2, 1, "Profesor X", 1));

        when(claseRepository.getClasesByAsignaturaId(asignaturaId)).thenReturn(clasesMock);

        List<Clase> result = claseService.getClasesByAsignaturaId(asignaturaId);

        assertNotNull(result);
        assertEquals(clasesMock, result);
        verify(claseRepository, times(1)).getClasesByAsignaturaId(asignaturaId);
    }

    @Test
    void testGetClasesBySalaIdNoResults() {
        int salaId = 999;  // Sala inexistente
        when(claseRepository.getClasesBySalaId(salaId)).thenReturn(new ArrayList<>());

        List<Clase> result = claseService.getClasesBySalaId(salaId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(claseRepository, times(1)).getClasesBySalaId(salaId);
    }

    @Test
    void testGetClasesByEdificioIdNoResults() {
        int edificioId = 999;  // Edificio inexistente
        when(claseRepository.getClasesByEdificioId(edificioId)).thenReturn(new ArrayList<>());

        List<Clase> result = claseService.getClasesByEdificioId(edificioId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(claseRepository, times(1)).getClasesByEdificioId(edificioId);
    }

    @Test
    void testGetClasesByAsignaturaIdNoResults() {
        int asignaturaId = 999;  // Asignatura inexistente
        when(claseRepository.getClasesByAsignaturaId(asignaturaId)).thenReturn(new ArrayList<>());

        List<Clase> result = claseService.getClasesByAsignaturaId(asignaturaId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(claseRepository, times(1)).getClasesByAsignaturaId(asignaturaId);
    }
}

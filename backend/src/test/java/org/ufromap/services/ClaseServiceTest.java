package org.ufromap.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Clase;
import org.ufromap.repositories.ClaseRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    void testFindAll() {
        List<Clase> clasesMock = new ArrayList<>();
        clasesMock.add(new Clase(1, 101, 1, 1, 2, 1, "Profesor X", 1));
        clasesMock.add(new Clase(2, 102, 2, 1, 3, 2, "Profesor Y", 2));

        when(claseRepository.findAll()).thenReturn(clasesMock);

        List<Clase> result = claseService.findAll();

        assertNotNull(result);
        assertEquals(clasesMock, result);
        verify(claseRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        int id = 1;
        Clase claseMock = new Clase(id, 101, 1, 1, 2, 1, "Profesor X", 1);
        when(claseRepository.findById(id)).thenReturn(claseMock);

        Clase result = claseService.findById(id);

        assertNotNull(result);
        assertEquals(claseMock, result);
        verify(claseRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        int id = 999;  // ID inexistente
        when(claseRepository.findById(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> claseService.findById(id));
        verify(claseRepository, times(1)).findById(id);
    }

    @Test
    void testFindByFilter() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("salaId", 101);

        List<Clase> clasesMock = new ArrayList<>();
        clasesMock.add(new Clase(1, 101, 1, 1, 2, 1, "Profesor X", 1));

        when(claseRepository.findByFilter(filters)).thenReturn(clasesMock);

        List<Clase> result = claseService.findByFilter(filters);

        assertNotNull(result);
        assertEquals(clasesMock, result);
        verify(claseRepository, times(1)).findByFilter(filters);
    }

    @Test
    void testFindByFilterNoResults() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("salaId", 999);  // Sala inexistente
        when(claseRepository.findByFilter(filters)).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> claseService.findByFilter(filters));
        verify(claseRepository, times(1)).findByFilter(filters);
    }

    @Test
    void testAddValidClase() {
        Clase clase = new Clase(1, 101, 1, 1, 2, 1, "Profesor X", 1);

        when(claseRepository.add(clase)).thenReturn(clase);

        Clase result = claseService.add(clase);

        assertNotNull(result);
        assertEquals(clase, result);
        verify(claseRepository, times(1)).add(clase);
    }

    @Test
    void testAddInvalidClase() {
        Clase clase = new Clase(1, 101, 1, 1, 8, 1, "Profesor X", 1);  // Día de la semana inválido

        assertThrows(BadRequestException.class, () -> claseService.add(clase));
        verify(claseRepository, never()).add(any());
    }

    @Test
    void testUpdateValidClase() {
        Clase claseExistente = new Clase(1, 101, 1, 1, 2, 1, "Profesor X", 1);
        Clase claseActualizada = new Clase(1, 101, 1, 1, 2, 2, "Profesor Y", 2);

        when(claseRepository.findById(claseExistente.getId())).thenReturn(claseExistente);
        when(claseRepository.update(claseActualizada)).thenReturn(claseActualizada);

        Clase result = claseService.update(claseActualizada);

        assertNotNull(result);
        assertEquals(claseActualizada, result);
        verify(claseRepository, times(1)).update(claseActualizada);
    }

    @Test
    void testUpdateInvalidClase() {
        Clase clase = new Clase(1, 101, 1, 1, 2, 12, "Profesor X", 1);  // Periodo inválido

        assertThrows(BadRequestException.class, () -> claseService.update(clase));
        verify(claseRepository, never()).update(any());
    }

    @Test
    void testDeleteExistingClase() {
        int id = 1;
        Clase claseMock = new Clase(id, 101, 1, 1, 2, 1, "Profesor X", 1);

        when(claseRepository.findById(id)).thenReturn(claseMock);

        claseService.delete(id);

        verify(claseRepository, times(1)).delete(id);
    }

    @Test
    void testDeleteNonExistingClase() {
        int id = 999;  // ID inexistente
        when(claseRepository.findById(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> claseService.delete(id));
        verify(claseRepository, never()).delete(id);
    }
}

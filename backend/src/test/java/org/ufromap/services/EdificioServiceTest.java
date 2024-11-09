package org.ufromap.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ufromap.models.Edificio;
import org.ufromap.repositories.EdificioRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EdificioServiceTest {

    @InjectMocks
    private EdificioService edificioService;

    @Mock
    private EdificioRepository edificioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEdificios_Success() {
        // Configuración de la lista de edificios
        List<Edificio> edificios = new ArrayList<>();
        edificios.add(new Edificio(1, "Edificio A", "Alias A", "Tipo A", 10.5f, -75.8f));
        when(edificioRepository.getEdificios()).thenReturn(edificios);

        // Ejecución del método de prueba
        List<Edificio> result = edificioService.getEdificios();

        // Verificación de resultados
        assertEquals(1, result.size());
        assertEquals("Edificio A", result.get(0).getNombre());
        verify(edificioRepository, times(1)).getEdificios();
    }

    @Test
    void testGetEdificios_EmptyList() {
        // Configuración de lista vacía
        when(edificioRepository.getEdificios()).thenReturn(new ArrayList<>());

        // Ejecución del método de prueba
        List<Edificio> result = edificioService.getEdificios();

        // Verificación de resultados
        assertTrue(result.isEmpty());
        verify(edificioRepository, times(1)).getEdificios();
    }

    @Test
    void testGetEdificioById_Success() {
        // Configuración de un edificio existente
        Edificio edificio = new Edificio(1, "Edificio B", "Alias B", "Tipo B", 15.5f, -80.2f);
        when(edificioRepository.getEdificioById(1)).thenReturn(edificio);

        // Ejecución del método de prueba
        Edificio result = edificioService.getEdificioById(1);

        // Verificación de resultados
        assertNotNull(result);
        assertEquals("Edificio B", result.getNombre());
        verify(edificioRepository, times(1)).getEdificioById(1);
    }

    @Test
    void testGetEdificioById_NotFound() {
        // Configuración de un ID inexistente
        when(edificioRepository.getEdificioById(2)).thenReturn(null);

        // Ejecución del método de prueba
        Edificio result = edificioService.getEdificioById(2);

        // Verificación de resultados
        assertNull(result);
        verify(edificioRepository, times(1)).getEdificioById(2);
    }

    @Test
    void testAddEdificio_Success() {
        // Configuración de un nuevo edificio
        Edificio edificio = new Edificio(0, "Edificio C", "Alias C", "Tipo C", 20.5f, -85.5f);
        when(edificioRepository.addEdificio(edificio)).thenReturn(true);

        // Ejecución del método de prueba
        boolean result = edificioService.addEdificio(edificio);

        // Verificación de resultados
        assertTrue(result);
        verify(edificioRepository, times(1)).addEdificio(edificio);
    }

    @Test
    void testAddEdificio_Failure() {
        // Configuración para un caso de error al agregar el edificio
        Edificio edificio = new Edificio(0, "Edificio D", "Alias D", "Tipo D", 25.5f, -90.5f);
        when(edificioRepository.addEdificio(edificio)).thenReturn(false);

        // Ejecución del método de prueba
        boolean result = edificioService.addEdificio(edificio);

        // Verificación de resultados
        assertFalse(result);
        verify(edificioRepository, times(1)).addEdificio(edificio);
    }

    @Test
    void testUpdateEdificio_Success() {
        // Configuración de un edificio existente a actualizar
        Edificio edificio = new Edificio(1, "Edificio E", "Alias E", "Tipo E", 30.5f, -95.5f);
        when(edificioRepository.updateEdificio(edificio)).thenReturn(true);

        // Ejecución del método de prueba
        boolean result = edificioService.updateEdificio(edificio);

        // Verificación de resultados
        assertTrue(result);
        verify(edificioRepository, times(1)).updateEdificio(edificio);
    }

    @Test
    void testUpdateEdificio_Failure() {
        // Configuración para un caso de error al actualizar el edificio
        Edificio edificio = new Edificio(1, "Edificio F", "Alias F", "Tipo F", 35.5f, -100.5f);
        when(edificioRepository.updateEdificio(edificio)).thenReturn(false);

        // Ejecución del método de prueba
        boolean result = edificioService.updateEdificio(edificio);

        // Verificación de resultados
        assertFalse(result);
        verify(edificioRepository, times(1)).updateEdificio(edificio);
    }

    @Test
    void testDeleteEdificio_Success() {
        // Configuración para eliminar un edificio existente
        int edificioId = 1;
        when(edificioRepository.deleteEdificio(edificioId)).thenReturn(true);

        // Ejecución del método de prueba
        boolean result = edificioService.deleteEdificio(edificioId);

        // Verificación de resultados
        assertTrue(result);
        verify(edificioRepository, times(1)).deleteEdificio(edificioId);
    }

    @Test
    void testDeleteEdificio_Failure() {
        // Configuración para un caso de error al eliminar el edificio
        int edificioId = 2;
        when(edificioRepository.deleteEdificio(edificioId)).thenReturn(false);

        // Ejecución del método de prueba
        boolean result = edificioService.deleteEdificio(edificioId);

        // Verificación de resultados
        assertFalse(result);
        verify(edificioRepository, times(1)).deleteEdificio(edificioId);
    }
}

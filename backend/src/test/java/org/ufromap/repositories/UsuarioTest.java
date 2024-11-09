package org.ufromap.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Usuario;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private Usuario usuario;
    private Set<Asignatura> asignaturas;

    @BeforeEach
    void setUp() {
        asignaturas = new HashSet<>();
        asignaturas.add(new Asignatura(3, "Matematicas", "ICF-103", "Mate para informatica", 4));
        usuario = new Usuario("Juan", "segura2023", "juan@example.com", asignaturas);
    }

    @Test
    void testIniciarSesionExitoso() {
        Optional<String> resultado = usuario.iniciarSesion("juan@example.com", "segura2023", "juan@example.com", "segura2023");
        assertTrue(resultado.isPresent());
        assertEquals("Inicio de sesión exitoso", resultado.get());
    }

    @Test
    void testIniciarSesionFallido() {
        Optional<String> resultado = usuario.iniciarSesion("juan@example.com", "incorrecta", "juan@example.com", "segura2023");
        assertFalse(resultado.isPresent());
    }

    @Test
    void testCambiarContraseniaExitoso() {
        Optional<String> nuevaContrasenia = usuario.cambiarContrasenia("segura2023", "nuevaSegura123");
        assertTrue(nuevaContrasenia.isPresent());
        assertEquals("nuevaSegura123", nuevaContrasenia.get());
    }

    @Test
    void testCambiarContraseniaFallido() {
        Optional<String> nuevaContrasenia = usuario.cambiarContrasenia("segura2023", "12345");
        assertFalse(nuevaContrasenia.isPresent());
    }

    @Test
    void testValidarContraseniaValida() {
        assertTrue(usuario.validarContrasenia("nuevaContrasenia123"));
    }

    @Test
    void testValidarContraseniaInvalidaComun() {
        assertFalse(usuario.validarContrasenia("123456"));
    }

    @Test
    void testValidarContraseniaInvalidaCorta() {
        assertFalse(usuario.validarContrasenia("corta"));
    }

    @Test
    void testCambiarCorreoExitoso() {
        Optional<String> nuevoCorreo = usuario.cambiarCorreo("juan@example.com", "nuevo@example.com", "juan@example.com");
        assertTrue(nuevoCorreo.isPresent());
        assertEquals("nuevo@example.com", nuevoCorreo.get());
    }

    @Test
    void testCambiarCorreoFallido() {
        Optional<String> nuevoCorreo = usuario.cambiarCorreo("incorrecto@example.com", "nuevo@example.com", "juan@example.com");
        assertFalse(nuevoCorreo.isPresent());
    }

    @Test
    void testRegistrarAsignaturaExitoso() {
        Asignatura nuevaAsignatura = new Asignatura(3, "Historia", "ICF-794", "Historia general", 10);
        Optional<Set<Asignatura>> nuevasAsignaturas = usuario.registrarAsignatura(nuevaAsignatura, asignaturas);
        assertTrue(nuevasAsignaturas.isPresent());
        assertTrue(nuevasAsignaturas.get().contains(nuevaAsignatura));
    }

    @Test
    void testRegistrarAsignaturaFallido() {
        Asignatura asignaturaExistente = asignaturas.iterator().next();
        Optional<Set<Asignatura>> nuevasAsignaturas = usuario.registrarAsignatura(asignaturaExistente, asignaturas);
        assertFalse(nuevasAsignaturas.isPresent());
    }
}

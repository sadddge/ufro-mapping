package org.ufromap.auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ValidatorTest {

    // Pruebas para validar correos electrónicos
    @Test
    void testValidEmail() {
        assertTrue(Validator.isValidEmail("usuario@ejemplo.com"), "Correo válido debería pasar");
        assertTrue(Validator.isValidEmail("nombre.apellido@sub.dominio.org"), "Correo válido debería pasar");
        assertTrue(Validator.isValidEmail("correo-usuario@dominio.net"), "Correo válido debería pasar");
    }

    @Test
    void testInvalidEmail() {
        assertFalse(Validator.isValidEmail("usuario@.com"), "Correo con dominio vacío después de @ no debería pasar");
        assertFalse(Validator.isValidEmail("@dominio.com"), "Correo sin parte local antes de @ no debería pasar");
        assertFalse(Validator.isValidEmail("usuario@dominio"), "Correo sin extensión de dominio no debería pasar");
        assertFalse(Validator.isValidEmail("usuario@@dominio.com"), "Correo con doble @ no debería pasar");
        assertFalse(Validator.isValidEmail("usuario@dominio..com"), "Correo con puntos consecutivos no debería pasar");
        assertFalse(Validator.isValidEmail(null), "Correo null no debería pasar");
        assertFalse(Validator.isValidEmail(""), "Correo vacío no debería pasar");
    }

    // Pruebas para validar contraseñas
    @Test
    void testValidPassword() {
        assertTrue(Validator.isValidPassword("P@ssw0rd"), "Contraseña válida debería pasar");
        assertTrue(Validator.isValidPassword("S3gura!2023"), "Contraseña válida debería pasar");
        assertTrue(Validator.isValidPassword("Val!dPass1"), "Contraseña válida debería pasar");
    }

    @Test
    void testInvalidPassword() {
        assertFalse(Validator.isValidPassword("short"), "Contraseña demasiado corta no debería pasar");
        assertFalse(Validator.isValidPassword("alllowercase"), "Contraseña sin mayúsculas no debería pasar");
        assertFalse(Validator.isValidPassword("ALLUPPERCASE"), "Contraseña sin minúsculas no debería pasar");
        assertFalse(Validator.isValidPassword("NoSpecialChar1"), "Contraseña sin caracteres especiales no debería pasar");
        assertFalse(Validator.isValidPassword("NoNumbers!"), "Contraseña sin números no debería pasar");
        assertFalse(Validator.isValidPassword(null), "Contraseña null no debería pasar");
        assertFalse(Validator.isValidPassword(""), "Contraseña vacía no debería pasar");
    }
}
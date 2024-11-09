package org.ufromap.auth;
import org.junit.jupiter.api.Test;
import org.ufromap.exceptions.BadRequestException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {

    @Test
    void testValidateEmail_invalidEmail1() {
        // Este debería lanzar una excepción ya que el correo no tiene formato válido del dominio
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("user@com"));
    }

    @Test
    void testValidateEmail_invalidEmail2() {
        // Este debería lanzar una excepción ya que el correo no tiene @
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("user.com"));
    }

    @Test
    void testValidateEmail_invalidEmail3() {
        // Este debería lanzar una excepción ya que el correo no tiene caracteres antes del @
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("@test.com"));
    }

    @Test
    void testValidateEmail_invalidEmail4() {
        // Este debería lanzar una excepción ya que el correo no tiene dominio
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("user.test@"));
    }

    @Test
    void testValidateEmail_EmptyEmail() {
        // Este debería lanzar una excepción ya que el correo está vacío
        assertThrows(BadRequestException.class, () -> Validator.validateEmail(""));
    }

    @Test
    void testValidateEmail_NullEmail() {
        // Este debería lanzar una excepción ya que el correo es null
        assertThrows(BadRequestException.class, () -> Validator.validateEmail(null));
    }

    @Test
    void testValidateEmail_InvalidEmailFormat() {
        // Este debería lanzar una excepción ya que el correo tiene formato inválido
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("user@com"));
    }

    @Test
    void testValidateEmail_ValidPassword() {
        // Este debería lanzar una excepcion si la contraseña no cumple con todos los criterios
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("Valid1@"));
    }

    @Test
    void testValidateEmail_ShortPassword() {
        // Este debería lanzar una excepción ya que la contraseña es demasiado corta
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("Abc@1"));
    }

    @Test
    void testValidateEmail_NoUpperCase() {
        // Este debería lanzar una excepción ya que la contraseña no tiene mayúsculas
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("password1@"));
    }

    @Test
    void testValidateEmail_NoLowerCase() {
        // Este debería lanzar una excepción ya que la contraseña no tiene minúsculas
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("PASSWORD1@"));
    }

    @Test
    void testValidateEmail_NoDigit() {
        // Este debería lanzar una excepción ya que la contraseña no tiene dígitos
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("Password@"));
    }

    @Test
    void testValidateEmail_NoSpecialCharacter() {
        // Este debería lanzar una excepción ya que la contraseña no tiene caracteres especiales
        assertThrows(BadRequestException.class, () -> Validator.validateEmail("Password1"));
    }

    @Test
    void testValidateEmail_NullPassword() {
        // Este debería lanzar una excepción ya que la contraseña es null
        assertThrows(BadRequestException.class, () -> Validator.validateEmail(null));
    }
}

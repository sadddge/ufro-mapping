package org.ufromap.models;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Representa un usuario con atributos de nombre, contraseña, correo y asignaturas.
 * Proporciona métodos para gestionar la sesión y actualizar información del usuario.
 */
public class Usuario {
    private String nombre;
    private String contrasenia;
    private String correo;
    private Set<Asignatura> asignaturas;

    private static final Set<String> CONTRASENIAS_COMUNES = Set.of(
        "123456", "password", "123456789", "12345678", "12345", "1234567", "1234567890", "qwerty", "abc123", "password1", "jordan23"
    );

    /**
     * Constructor por defecto de Usuario no registrado.
     */
    public Usuario() {

    }

    /**
     * Constructor que inicializa un usuario con un nombre, contraseña, correo y un conjunto de asignaturas.
     *
     * @param nombre el nombre del usuario
     * @param contrasenia la contraseña del usuario
     * @param correo el correo del usuario
     * @param asignaturas el conjunto de asignaturas del usuario
     */
    public Usuario(String nombre, String contrasenia, String correo, Set<Asignatura> asignaturas) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.asignaturas = asignaturas;
    }

    /**
     * Muestra las asignaturas del usuario en la consola.
     */
    public void mostrarAsignaturas() {
        System.out.println("Asignaturas: " + asignaturas);
    }

    /**
     * Intenta iniciar sesión comparando el correo y la contraseña proporcionados con los almacenados.
     *
     * @param correo el correo proporcionado para iniciar sesión
     * @param contrasenia la contraseña proporcionada para iniciar sesión
     * @param correoAlmacenado el correo almacenado en el sistema
     * @param contraseniaAlmacenada la contraseña almacenada en el sistema
     * @return un Optional con un mensaje de éxito si los datos coinciden, o un Optional vacío si no coinciden
     */
    public Optional<String> iniciarSesion(String correo, String contrasenia, String correoAlmacenado, String contraseniaAlmacenada) {
        if (correo.equals(correoAlmacenado) && contrasenia.equals(contraseniaAlmacenada)) {
            return Optional.of("Inicio de sesión exitoso");
        }
        return Optional.empty();
    }

    /**
     * Cierra la sesión del usuario, mostrando un mensaje en la consola.
     */
    public void cerrarSesion() {
        System.out.println("Sesión cerrada");
    }

    /**
     * Cambia la contraseña del usuario si la nueva contraseña es válida.
     *
     * @param contraseniaActual la contraseña actual del usuario
     * @param contraseniaNueva la nueva contraseña a establecer
     * @return un Optional con la nueva contraseña si es válida, o un Optional vacío si no lo es
     */
    public Optional<String> cambiarContrasenia(String contraseniaActual, String contraseniaNueva) {
        return validarContrasenia(contraseniaNueva) ? Optional.of(contraseniaNueva) : Optional.empty();
    }

    /**
     * Valida si una contraseña es segura, basándose en la longitud y en que no sea una contraseña común.
     *
     * @param contrasenia la contraseña a validar
     * @return true si la contraseña es válida, false de lo contrario
     */
    public boolean validarContrasenia(String contrasenia) {
        Predicate<String> longitudValida = c -> c.length() >= 10;
        Predicate<String> noEsComun = c -> !CONTRASENIAS_COMUNES.contains(c);
    
        return longitudValida.and(noEsComun).test(contrasenia);
    }

    /**
     * Cambia el correo del usuario si el correo actual coincide con el registrado.
     *
     * @param correoActual el correo actual del usuario
     * @param correoNuevo el nuevo correo a establecer
     * @param correo el correo registrado en el sistema
     * @return un Optional con el nuevo correo si el cambio es válido, o un Optional vacío si no lo es
     */
    public Optional<String> cambiarCorreo(String correoActual, String correoNuevo, String correo) {
        return correoActual.equals(correo) ? Optional.of(correoNuevo) : Optional.empty();
    }

    /**
     * Registra una asignatura para el usuario si no está ya registrada.
     *
     * @param asignatura la asignatura a registrar
     * @param asignaturas el conjunto de asignaturas actuales
     * @return un Optional con el nuevo conjunto de asignaturas si el registro es exitoso, o un Optional vacío si ya estaba registrada
     */
    public Optional<Set<Asignatura>> registrarAsignatura(Asignatura asignatura, Set<Asignatura> asignaturas) {
        if (asignaturas.contains(asignatura)) {
            System.out.println("La asignatura ya está registrada");
            return Optional.empty();
        }
        
        Set<Asignatura> nuevasAsignaturas = new HashSet<>(asignaturas);
        nuevasAsignaturas.add(asignatura);
        return Optional.of(nuevasAsignaturas);
    }
}

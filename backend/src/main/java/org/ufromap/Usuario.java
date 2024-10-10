package org.ufromap;

import java.util.List;

public class Usuario implements InfoMostrable, ClaseMostrable {
    private String nombre;
    private String contrasenia;
    private String correo;
    private Set<Asignatura> asignaturas;

    private static final Set<String> CONTRASENIAS_COMUNES = Set.of(
        "123456", "password", "123456789", "12345678", "12345", "1234567", "1234567890", "qwerty", "abc123", "password1", "jordan23"
    );

    public Usuario() {

    }

    public Usuario(String nombre, String contrasenia, String correo, Set<Asignatura> asignaturas) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.asignaturas = asignaturas;
    }

    public void mostrarAsignaturas() {
        System.out.println("Asignaturas: " + asignaturas);
    }

    public Optional<String> iniciarSesion(String correo, String contrasenia, String correoAlmacenado, String contraseniaAlmacenada) {
        if (correo.equals(correoAlmacenado) && contrasenia.equals(contraseniaAlmacenada)) {
            return Optional.of("Inicio de sesión exitoso");
        }
        return Optional.empty();
    }
    

    public void cerrarSesion() {
        System.out.println("Sesión cerrada");
    }

    public Optional<String> cambiarContrasenia(String contraseniaActual, String contraseniaNueva) {
        return validarContrasenia(contraseniaNueva) ? Optional.of(contraseniaNueva) : Optional.empty();
    }

    public boolean validarContrasenia(String contrasenia) {
        Predicate<String> longitudValida = c -> c.length() >= 10;
        Predicate<String> noEsComun = c -> !CONTRASENIAS_COMUNES.contains(c);
    
        return longitudValida.and(noEsComun).test(contrasenia);
    }

    public Optional<String> cambiarCorreo(String correoActual, String correoNuevo, String correo) {
        return correoActual.equals(correo) ? Optional.of(correoNuevo) : Optional.empty();
    }
    

    public Optional<Set<Asignatura>> registrarAsignatura(Asignatura asignatura, Set<Asignatura> asignaturas) {
        if (asignaturas.contains(asignatura)) {
            System.out.println("La asignatura ya está registrada");
            return Optional.empty();
        }
        
        Set<Asignatura> nuevasAsignaturas = new HashSet<>(asignaturas);
        nuevasAsignaturas.add(asignatura);
        return Optional.of(nuevasAsignaturas);
    }
    

    @Override
    public void mostrarClases() {
        System.out.println("Clases: ");
        for (Asignatura asignatura : asignaturas) {
            asignatura.mostrarClases();
        }
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        mostrarAsignaturas();
    }
}

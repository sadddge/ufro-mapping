package org.ufromap;

import java.util.List;

public class Usuario implements InfoMostrable, ClaseMostrable {
    private String nombre;
    private String contrasenia;
    private String correo;
    private List<Asignatura> asignaturas;

    private static final List<String> contraseniasComunes = List.of(
        "123456", "password", "123456789", "12345678", "12345", "1234567", "1234567890", "qwerty", "abc123", "password1", "jordan23"
    );

    public Usuario() {

    }

    public Usuario(String nombre, String contrasenia, String correo, List<Asignatura> asignaturas) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.asignaturas = asignaturas;
    }

    public void mostrarAsignaturas() {
        System.out.println("Asignaturas: " + asignaturas);
    }

    public boolean iniciarSesion(String correo, String contrasenia) {
        return correo.equals(this.correo) && contrasenia.equals(this.contrasenia);
    }

    public void cerrarSesion() {
        System.out.println("Sesión cerrada");
    }

    public boolean cambiarContrasenia(String contraseniaActual, String contraseniaNueva) {
        if (validarContrasenia(contraseniaNueva)) {
            contrasenia = contraseniaNueva;
            return true;
        }
        return false;
    }

    public boolean validarContrasenia(String contrasenia) {
        return contrasenia.length() < 10 || contraseniasComunes.contains(contrasenia);
    }

    public boolean cambiarCorreo(String correoActual, String correoNuevo) {
        if (correoActual.equals(correo)) {
            correo = correoNuevo;
            return true;
        }
        return false;
    }

    public boolean registrarAsignatura(Asignatura asignatura) {
        if (!asignaturas.contains(asignatura)) {
            asignaturas.add(asignatura);
            return true;
        }
        System.out.println("La asignatura ya está registrada");
        return false;
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

package project.ufro;

import java.util.List;

public class Usuario implements InfoMostrable, ClaseMostrable {
    private String nombre;
    private String contrasenia;
    private String correo;
    private List<Asignatura> asignaturas;

    public Usuario() {

    }

    public Usuario(String nombre, String contrasenia, String correo, List<Asignatura> asignaturas) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.asignaturas = asignaturas;
    }

    public void mostrarAsignaturas() {

    }

    public boolean iniciarSesion(String correo, String contrasenia) {
        return false;
    }

    public void cerrarSesion() {

    }

    public boolean cambiarContrasenia(String contraseniaActual, String contraseniaNueva) {
        return false;
    }

    public boolean cambiarCorreo(String correoActual, String correoNuevo) {
        return false;
    }

    public boolean registrarAsignatura(Asignatura asignatura) {
        return false;
    }

    @Override
    public void mostrarClases() {

    }

    @Override
    public void mostrarInformacion() {

    }
}

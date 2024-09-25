package project.ufro;

import java.util.List;

public class Asignatura implements InfoMostrable, ClaseMostrable {
    private String nombre;
    private String codigo;
    private String descripcion;
    private int SCT;
    private List<Clase> clases;

    public Asignatura(String nombre, String codigo, String descripcion, int SCT, List<Clase> clases) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.SCT = SCT;
        this.clases = clases;
    }

    @Override
    public void mostrarClases() {

    }

    @Override
    public void mostrarInformacion() {

    }
}

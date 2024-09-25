package project.ufro;

import java.util.List;

public class Edificio implements InfoMostrable, ClaseMostrable {
    private long id;
    private String nombre;
    private String alias;
    private String ubicacion;
    private String tipo;
    private List<Sala> salas;

    public Edificio(long id, String nombre, String alias, String ubicacion, String tipo, List<Sala> salas) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.ubicacion = ubicacion;
        this.tipo = tipo;
        this.salas = salas;
    }

    @Override
    public void mostrarClases() {

    }

    @Override
    public void mostrarInformacion() {

    }
}

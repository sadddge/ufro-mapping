package project.ufro;

public class Clase implements InfoMostrable {
    private String docente;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private int modulo;
    private Sala sala;

    public Clase(String docente, String diaSemana, String horaInicio, String horaFin, int modulo, Sala sala) {
        this.docente = docente;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modulo = modulo;
        this.sala = sala;
    }

    @Override
    public void mostrarInformacion() {

    }
}

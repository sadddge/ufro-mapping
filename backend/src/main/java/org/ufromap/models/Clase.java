package org.ufromap.models;

/**
 * Representa una clase académica, incluyendo información sobre el docente, horario, y la sala asignada.
 */
public class Clase {

    private String docente;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private int modulo;
    private Sala sala;

    /**
     * Constructor que inicializa una clase académica con la información proporcionada.
     * 
     * @param docente El nombre del docente que dicta la clase.
     * @param diaSemana El día de la semana en que se dicta la clase.
     * @param horaInicio La hora de inicio de la clase (formato HH:MM).
     * @param horaFin La hora de término de la clase (formato HH:MM).
     * @param modulo El número de módulo al que pertenece la clase.
     * @param sala El objeto {@link Sala} que representa la sala donde se dicta la clase.
     */
    public Clase(String docente, String diaSemana, String horaInicio, String horaFin, int modulo, Sala sala) {
        this.docente = docente;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modulo = modulo;
        this.sala = sala;
    }

    /**
     * Obtiene el nombre del docente que dicta la clase.
     * 
     * @return El nombre del docente.
     */
    public String getDocente() {
        return docente;
    }

    /**
     * Establece el nombre del docente que dicta la clase.
     * 
     * @param docente El nombre del docente.
     */
    public void setDocente(String docente) {
        this.docente = docente;
    }

    /**
     * Obtiene el día de la semana en que se dicta la clase.
     * 
     * @return El día de la semana.
     */
    public String getDiaSemana() {
        return diaSemana;
    }

    /**
     * Establece el día de la semana en que se dicta la clase.
     * 
     * @param diaSemana El día de la semana.
     */
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     * Obtiene la hora de inicio de la clase.
     * 
     * @return La hora de inicio.
     */
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     * Establece la hora de inicio de la clase.
     * 
     * @param horaInicio La hora de inicio.
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la hora de término de la clase.
     * 
     * @return La hora de término.
     */
    public String getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de término de la clase.
     * 
     * @param horaFin La hora de término.
     */
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el número de módulo al que pertenece la clase.
     * 
     * @return El número de módulo.
     */
    public int getModulo() {
        return modulo;
    }

    /**
     * Establece el número de módulo al que pertenece la clase.
     * 
     * @param modulo El número de módulo.
     */
    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    /**
     * Obtiene la sala en la que se dicta la clase.
     * 
     * @return Un objeto {@link Sala} que representa la sala.
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * Establece la sala en la que se dicta la clase.
     * 
     * @param sala Un objeto {@link Sala} que representa la sala.
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }
}

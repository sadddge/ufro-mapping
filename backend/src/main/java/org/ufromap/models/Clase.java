package org.ufromap.models;

/**
 * Representa una clase académica, incluyendo información sobre el docente, horario, y la sala asignada.
 */
public class Clase {

    private int id;
    private int salaId;
    private int edificioId;
    private int asignaturaId;
    private int diaSemana;
    private int periodo;
    private String docente;
    private int modulo;

    /**
     * Constructor que inicializa una clase académica con la información proporcionada.
     *
     * @param id el id único de la clase.
     * @param salaId el id de la sala en la que se dicta la clase.
     * @param edificioId el id del edificio en el que se dicta la clase.
     * @param asignaturaId el id de la asignatura a la que pertenece la clase.
     * @param diaSemana el día de la semana en que se dicta la clase.
     * @param periodo el periodo en que se dicta la clase.
     * @param docente el nombre del docente que dicta la clase.
     * @param modulo el número de módulo al que pertenece la clase.
     */
    public Clase(int id, int salaId, int edificioId, int asignaturaId, int diaSemana, int periodo, String docente, int modulo) {
        this.id = id;
        this.salaId = salaId;
        this.edificioId = edificioId;
        this.asignaturaId = asignaturaId;
        this.diaSemana = diaSemana;
        this.periodo = periodo;
        this.docente = docente;
        this.modulo = modulo;
    }

    /**
     * Obtiene el id único de la clase.
     *
     * @return El id de la clase.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id único de la clase.
     *
     * @param id El id de la clase.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el id de la sala en la que se dicta la clase.
     *
     * @return El id de la sala.
     */
    public int getSalaId() {
        return salaId;
    }

    /**
     * Establece el id de la sala en la que se dicta la clase.
     *
     * @param salaId El id de la sala.
     */
    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }

    /**
     * Obtiene el id del edificio en el que se dicta la clase.
     *
     * @return El id del edificio.
     */
    public int getEdificioId() {
        return edificioId;
    }

    /**
     * Establece el id del edificio en el que se dicta la clase.
     *
     * @param edificioId El id del edificio.
     */
    public void setEdificioId(int edificioId) {
        this.edificioId = edificioId;
    }

    /**
     * Obtiene el id de la asignatura a la que pertenece la clase.
     *
     * @return El id de la asignatura.
     */
    public int getAsignaturaId() {
        return asignaturaId;
    }

    /**
     * Establece el id de la asignatura a la que pertenece la clase.
     *
     * @param asignaturaId El id de la asignatura.
     */
    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    /**
     * Obtiene el día de la semana en que se dicta la clase.
     *
     * @return El día de la semana.
     */
    public int getDiaSemana() {
        return diaSemana;
    }

    /**
     * Establece el día de la semana en que se dicta la clase.
     *
     * @param diaSemana El día de la semana.
     */
    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
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
}

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
    private String docenteNombre;
    private int modulo;

    public Clase(int id, int salaId, int edificioId, int asignaturaId, int diaSemana, int periodo, String docenteNombre, int modulo) {
        this.id = id;
        this.salaId = salaId;
        this.edificioId = edificioId;
        this.asignaturaId = asignaturaId;
        this.diaSemana = diaSemana;
        this.periodo = periodo;
        this.docenteNombre = docenteNombre;
        this.modulo = modulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalaId() {
        return salaId;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }

    public int getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(int edificioId) {
        this.edificioId = edificioId;
    }

    public int getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(int asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getDocenteNombre() {
        return docenteNombre;
    }

    public void setDocenteNombre(String docenteNombre) {
        this.docenteNombre = docenteNombre;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }
}






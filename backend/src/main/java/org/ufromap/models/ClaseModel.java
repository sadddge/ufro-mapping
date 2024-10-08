package org.ufromap.models;

import java.util.Arrays;
import java.util.List;

import org.ufromap.Sala;

public class ClaseModel {

    private String docente;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private int modulo;
    private Sala sala;

    public ClaseModel(String docente, String diaSemana, String horaInicio, String horaFin, int modulo, Sala sala) {
        this.docente = docente;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modulo = modulo;
        this.sala = sala;
    }

    public void mostrarInformacion() {
        List<String> infoClase = Arrays.asList(
            "Docente: " + docente,
            "Día de la semana: " + diaSemana,
            "Hora de inicio: " + horaInicio,
            "Hora de fin: " + horaFin,
            "Módulo: " + modulo,
            "Sala: " + (sala != null ? sala.getSala() : "No existe esta sala")
        );

        for (String info : infoClase) {
            System.out.println(info);
        }
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }




}
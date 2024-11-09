package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa una clase académica, incluyendo información sobre el docente, horario, y la sala asignada.
 */
@Getter
@Setter
public class Clase {

    private int id;
    @SerializedName("sala_id")
    private int salaId;
    @SerializedName("edificio_id")
    private int edificioId;
    @SerializedName("asignatura_id")
    private int asignaturaId;
    @SerializedName("dia_semana")
    private int diaSemana;
    private int periodo;
    @SerializedName("docente_nombre")
    private String docente;
    private int modulo;

    /**
     * Constructor que inicializa una clase académica con la información proporcionada.
     *
     * @param id           id único de la clase.
     * @param salaId       id de la sala en la que se dicta la clase.
     * @param edificioId   id del edificio en el que se dicta la clase.
     * @param asignaturaId id de la asignatura a la que pertenece la clase.
     * @param diaSemana    el día de la semana en que se dicta la clase.
     * @param periodo      el periodo en que se dicta la clase.
     * @param docente      el nombre del docente que dicta la clase.
     * @param modulo       el número del módulo al que pertenece la clase.
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
}

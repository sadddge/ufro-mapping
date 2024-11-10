package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa una clase académica, incluyendo información sobre el docente, horario, y la sala asignada.
 */
@Getter
@Setter
@AllArgsConstructor
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
}
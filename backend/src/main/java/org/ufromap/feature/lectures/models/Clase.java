package org.ufromap.feature.lectures.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * Representa una clase académica, incluyendo información sobre el docente, horario, y la sala asignada.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Clase {
    private int id;
    private int salaId;
    private int asignaturaId;
    private int diaSemana;
    private int periodo;
    private String docente;
    private int modulo;
}
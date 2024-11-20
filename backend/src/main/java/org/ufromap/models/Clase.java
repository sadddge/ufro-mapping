package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * Representa una clase académica, incluyendo información sobre el docente, horario, y la sala asignada.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Clase {

    private int id;
    private int salaId;
    private int edificioId;
    private int asignaturaId;
    private int diaSemana;
    private int periodo;
    private String docente;
    private int modulo;
}
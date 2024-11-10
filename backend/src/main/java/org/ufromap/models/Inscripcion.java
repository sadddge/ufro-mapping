package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Inscripcion {
    private int id;
    @SerializedName("usuario_id")
    private int usuarioId;
    @SerializedName("asignatura_id")
    private int asignaturaId;
}
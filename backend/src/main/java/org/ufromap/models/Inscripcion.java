package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inscripcion {
    private int id;
    @SerializedName("usuario_id")
    private int usuarioId;
    @SerializedName("asignatura_id")
    private int asignaturaId;
}
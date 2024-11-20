package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inscripcion {
    private int id;
    private int usuarioId;
    private int asignaturaId;
}
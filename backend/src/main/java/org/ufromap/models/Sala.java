package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

/**
 * Clase que representa una sala dentro de un edificio en el sistema.
 * Una sala está asociada a un edificio y puede tener una lista de clases que se imparten en ella.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    private int id;
    @SerializedName("edificio_id")
    private int edificioId;
    @SerializedName("nombre_sala")
    private String nombre;
    private List<Clase> clases;
}
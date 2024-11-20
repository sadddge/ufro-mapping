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
    private int edificioId;
    private String nombre;
    private List<Clase> clases;
}
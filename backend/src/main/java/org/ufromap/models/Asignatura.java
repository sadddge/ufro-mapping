package org.ufromap.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

/**
 * Clase que representa una Asignatura en el sistema.
 * Una Asignatura tiene un nombre, código, descripción, valor en SCT (Sistema de Créditos Transferibles),
 * y una lista de clases asociadas.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Asignatura {
    @Expose
    private int id;
    @Expose
    private String nombre;
    @Expose
    private String codigo;
    private String descripcion;
    private int sct;
    private List<Clase> clases;
}
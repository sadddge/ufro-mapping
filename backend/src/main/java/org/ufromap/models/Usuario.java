package org.ufromap.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasenia;
}
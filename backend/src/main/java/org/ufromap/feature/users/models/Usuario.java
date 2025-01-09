package org.ufromap.feature.users.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String rol;
    private String nombre;
    private String correo;
    private String contrasenia;
}
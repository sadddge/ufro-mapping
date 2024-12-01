package org.ufromap.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {
    private String rol;
    private String nombre;
    private String correo;
    private String contrasenia;
}

package org.ufromap.dto.response;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private int id;
    private String rol;
    private String nombre;
    private String correo;
}

package org.api.ufro_mapping.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 20)
    private String name;
    @Column(name = "correo", nullable = false, unique = true, length = 80)
    private String email;
    @Column(name = "contrasenia", nullable = false, length = 200)
    private String password;
    @ManyToOne
    @JoinColumn(name = "nombre_rol", nullable = false)
    private Role role;


    @ManyToMany
    @JoinTable(
            name = "inscribe",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "asignatura_id")
    )
    private Set<Course> courses;
}

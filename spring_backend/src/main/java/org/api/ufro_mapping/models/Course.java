package org.api.ufro_mapping.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
@Table(name = "asignatura")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 45)
    @Column(name = "nombre_asignatura", nullable = false, unique = true, length = 45)
    private String name;
    @NotBlank
    @Size(max = 45)
    @Column(name = "codigo", nullable = false, unique = true, length = 45)
    private String code;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String description;
    @Positive
    @Column(name = "sct")
    private Integer sct;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users;

    @OneToMany(mappedBy = "course")
    private Set<Lecture> lectures;
}

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
@Table(name = "sala")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 50)
    @Column(name = "nombre_sala", nullable = false, unique = true, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "edificio_id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "classroom")
    private Set<Lecture> lectures;
}

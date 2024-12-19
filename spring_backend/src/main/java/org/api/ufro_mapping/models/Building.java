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
@Table(name = "edificio")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 100)
    @Column(name = "nombre_edificio", nullable = false, unique = true, length = 100)
    private String name;
    @Size(max = 45)
    @Column(name = "alias", length = 45)
    private String alias;
    @Size(max = 45)
    @Column(name = "tipo", length = 45)
    private String type;
    @Column(name = "latitud", nullable = false)
    private double latitude;
    @Column(name = "longitud", nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "building")
    private Set<Classroom> classrooms;

}

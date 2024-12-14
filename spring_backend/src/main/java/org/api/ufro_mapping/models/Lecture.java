package org.api.ufro_mapping.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clase")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dia_semana", nullable = false)
    private int day;
    @Column(name = "periodo", nullable = false)
    private int period;
    @Column(name = "docente_nombre")
    private String teacherName;
    @Column(name = "modulo")
    private Integer module;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Course course;
}

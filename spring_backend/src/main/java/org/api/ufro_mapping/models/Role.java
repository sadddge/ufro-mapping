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
@Table(name = "rol")
public class Role {
    @Id
    @NotBlank
    @Size(max = 45)
    @Column(name = "nombre", length = 45, nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "role")
    private Set<User> users;
}

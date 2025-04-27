package model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "proyects")
public class Proyect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    // Relación de uno a muchos con Task
    @OneToMany(mappedBy = "proyect", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks; // Lista de tareas asociadas a este proyecto
}



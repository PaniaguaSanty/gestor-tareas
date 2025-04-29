package com.todoproject.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tittle;
    private String description;
    private TaskState taskState;

    // Relación de muchos a uno con Proyect
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyect_id") // La columna en la tabla de tareas que se refiere al proyecto
    private Project project; // Proyecto asociado a esta tarea
}

package com.todoproject.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;
    private String name;
    private String description; // breve información sobre el equipo/objetivo del mismo.
    private boolean active;
    // Un equipo agrupa varios usuarios
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    // Un equipo puede abarcar varios proyectos
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    /**
     * Garantiza que users nunca sea null y sincroniza ambas partes de la relación
     */
    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        if (!this.users.contains(user)) {
            this.users.add(user);
            user.setTeam(this);
        }
    }

    public void removeUser(User user) {
        if (this.users != null && this.users.remove(user)) {
            user.setTeam(null);
        }
    }

}



package com.todoproject.demo.repository;

import com.todoproject.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByCode(String code);

    Optional<Project> findByCode(String code);

    List<Project> findByTeam_Dni(String dni); // solo si lo usás en el servicio

}

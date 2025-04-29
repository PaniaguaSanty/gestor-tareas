package com.todoproject.demo.repository;

import com.todoproject.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectRepository extends JpaRepository<Project, Long> {
}

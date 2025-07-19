package com.todoproject.demo.repository;

import com.todoproject.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByCode(String code);
    List<Task> findByProject_Code(String code);
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.active = true")
    List<Task> findByProjectIdAndActiveTrue(@Param("projectId") Long projectId);
}

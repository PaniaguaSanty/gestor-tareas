package com.todoproject.demo.repository;

import com.todoproject.demo.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    Optional<Team> findByDni(String dni);

    // Para evitar colisiones al generar
    boolean existsByDni(String dni);

    @Query("SELECT t FROM Team t WHERE " +
           "(:search IS NULL OR :search = '' OR LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND t.active = :status")
    List<Team> search(@Param("search") String search, @Param("status") boolean status);
}

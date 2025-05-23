package com.dux.equipos_futbol.repository;

import com.dux.equipos_futbol.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findById(Long id);
    List<Team> findByNombreContainingIgnoreCase(String nombre);
}

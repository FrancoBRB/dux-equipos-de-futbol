package com.dux.equipos_futbol.service;

import com.dux.equipos_futbol.dto.CreateTeamDto;
import com.dux.equipos_futbol.dto.TeamDto;
import com.dux.equipos_futbol.model.Team;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService {
    public List<TeamDto> findAll();
    public ResponseEntity<?> getTeamById(Long id);
    public List<TeamDto> getByNombre(String nombre);
    public ResponseEntity<CreateTeamDto> saveTeam(CreateTeamDto team);
    public ResponseEntity<?> updateTeam(Long id, CreateTeamDto teamDto);
    public ResponseEntity<?> deleteTeam(Long id);
}

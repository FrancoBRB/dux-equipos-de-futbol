package com.dux.equipos_futbol.service.impl;

import com.dux.equipos_futbol.dto.ResponseDto;
import com.dux.equipos_futbol.dto.CreateTeamDto;
import com.dux.equipos_futbol.model.Team;
import com.dux.equipos_futbol.repository.TeamRepository;
import com.dux.equipos_futbol.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findAll(){
        List<Team> teams = teamRepository.findAll();
        return teams;
    }

    @Override
    public ResponseEntity<?> getTeamById(Long id){
        Optional<Team> team = teamRepository.findById(id);
        if(team.isPresent()) return ResponseEntity.ok(team.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ResponseDto("Equipo no encontrado", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public List<Team> getByNombre(String nombre){
        List<Team> teams = teamRepository.findByNombreContainingIgnoreCase(nombre);
        return teams;
    }

    @Override
    public ResponseEntity<?> saveTeam(CreateTeamDto createTeamDto){
        Team team  = teamRepository.save(this.mapCreateTeamDtoToTeam(createTeamDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(createTeamDto);
    }

    @Override
    public ResponseEntity<?> updateTeam(Long id, CreateTeamDto teamDto){
        Optional<Team> team = teamRepository.findById(id);
        if(!team.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ResponseDto("Equipo no encontrado", HttpStatus.NOT_FOUND.value()));
        }
        team.get().setNombre(teamDto.getNombre());
        team.get().setPais(teamDto.getPais());
        team.get().setLiga(teamDto.getLiga());
        return ResponseEntity.ok(teamRepository.save(team.get()));
    }

    @Override
    public ResponseEntity<?> deleteTeam(Long id){
        Optional<Team> team = teamRepository.findById(id);
        if(!team.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new ResponseDto("Equipo no encontrado", HttpStatus.NOT_FOUND.value()));
        }
        teamRepository.delete(team.get());
        return ResponseEntity.noContent().build();
    }

    private Team mapCreateTeamDtoToTeam(CreateTeamDto createTeamDto){
        Team team = new Team();
        team.setNombre(createTeamDto.getNombre());
        team.setPais(createTeamDto.getPais());
        team.setLiga(createTeamDto.getLiga());
        return team;
    }
}
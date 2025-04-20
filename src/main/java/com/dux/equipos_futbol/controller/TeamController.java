package com.dux.equipos_futbol.controller;

import com.dux.equipos_futbol.dto.CreateTeamDto;
import com.dux.equipos_futbol.dto.ResponseDto;
import com.dux.equipos_futbol.dto.TeamDto;
import com.dux.equipos_futbol.model.Team;
import com.dux.equipos_futbol.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipos")
@CrossOrigin
 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Slf4j
public class TeamController {

    private final TeamService teamService;
    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los equipos", description = "Devuelve una lista con todos los equipos registrados")
    public ResponseEntity<List<TeamDto>> getAllTeams(){
        logger.info("Requesting -> GET /equipos/");
        return ResponseEntity.ok(this.teamService.findAll());
    }

    @Operation(summary = "Obtener equipo por id", description = "Obtiene un equipo por su id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id){
        logger.info("Requesting -> GET /equipos/" + id);
        return this.teamService.getTeamById(id);
    }

    @Operation(summary = "Obtener todos los equipos por nombre", description = "Devuelve una lista con todos los equipos filtrado por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<List<TeamDto>> getTeamsByName(@RequestParam String name){
        logger.info("Requesting -> GET /equipos/buscar?="+ name);
        return ResponseEntity.ok(this.teamService.getByNombre(name));
    }

    @Operation(summary = "Crear equipo", description = "Crea un equipo y lo guarda en la base de datos")
    @PostMapping
    public ResponseEntity<?> createTeam(@Valid @RequestBody CreateTeamDto team, BindingResult bindingResult){
        logger.info("Requesting -> POST /equipos/ with data ({})", team );

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ResponseDto("La solicitud es invalida", HttpStatus.BAD_REQUEST.value()));
        }

        return this.teamService.saveTeam(team);
    }

    @Operation(summary = "Actualizar equipo", description = "Actualiza la informacion de un equipo")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@Valid @RequestBody CreateTeamDto team, BindingResult bindingResult, @PathVariable Long id){
        logger.info("Requesting -> PUT /equipos/" + id + " with data ({})", team );

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ResponseDto("La solicitud es invalida", HttpStatus.BAD_REQUEST.value()));
        }

        return this.teamService.updateTeam(id,team);
    }

    @Operation(summary = "Eliminar equipo", description = "Elimina un equipo de la base de datos")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id){
        logger.info("Requesting -> DELETE /equipos/" + id);
        return this.teamService.deleteTeam(id);
    }
}
package com.dux.equipos_futbol.controller;

import com.dux.equipos_futbol.dto.CreateTeamDto;
import com.dux.equipos_futbol.dto.ResponseDto;
import com.dux.equipos_futbol.dto.TeamDto;
import com.dux.equipos_futbol.service.TeamService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

@SpringBootTest
public class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @Test
    void findAllTeamTest(){
        TeamDto team1 = new TeamDto(1L, "Union de Santa Fe", "Liga Argentina", "Argentina");
        TeamDto team2 = new TeamDto(2L, "Colon de Santa Fe", "Liga Argentina", "Argentina");
        List<TeamDto> mockTeams = List.of(team1, team2);

        when(teamService.findAll()).thenReturn(mockTeams);

        ResponseEntity<List<TeamDto>> response = teamController.getAllTeams();

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(2, response.getBody().size());
        Assertions.assertEquals("Union de Santa Fe", response.getBody().get(0).getNombre());
        Assertions.assertEquals("Colon de Santa Fe", response.getBody().get(1).getNombre());
    }

    @Test
    void createInvalidTeamTest() {
        CreateTeamDto createTeamDto = new CreateTeamDto(null, "", "");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = teamController.createTeam(createTeamDto, bindingResult);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertTrue(response.getBody() instanceof ResponseDto);
    }

    @Test
    void createValidTeam() {
        CreateTeamDto createTeamDto = new CreateTeamDto("Union de Santa Fe", "Liga Argentina", "Argentina");
        TeamDto createdTeamDto = new TeamDto(1L, "Union de Santa Fe", "Liga Argentina", "Argentina");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<TeamDto> mockResponse =
                ResponseEntity.status(HttpStatus.CREATED).body(createdTeamDto);

        when(teamService.saveTeam(any(CreateTeamDto.class))).thenReturn(mockResponse);

        ResponseEntity<?> response = teamController.createTeam(createTeamDto, bindingResult);

        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertTrue(response.getBody() instanceof TeamDto);

        TeamDto responseBody = (TeamDto) response.getBody();
        Assertions.assertEquals("Union de Santa Fe", responseBody.getNombre());
        Assertions.assertEquals("Liga Argentina", responseBody.getLiga());
        Assertions.assertEquals("Argentina", responseBody.getPais());
    }
}

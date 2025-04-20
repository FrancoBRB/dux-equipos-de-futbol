package com.dux.equipos_futbol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

    private Long id;

    @NotBlank(message = "El nombre no puede ser nulo o vacio")
    private String nombre;

    @NotBlank(message = "La liga no puede ser nula o vacia")
    private String liga;

    @NotBlank(message = "El pais no puede ser nulo o vacio")
    private String pais;
}

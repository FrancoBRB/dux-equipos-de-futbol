package com.dux.equipos_futbol.security;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public JwtResponse setToken(String token) {
        this.token = token;
        return this;
    }
}
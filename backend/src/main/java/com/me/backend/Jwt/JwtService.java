package com.me.backend.Jwt;

import io.jsonwebtoken.Claims;

public interface JwtService {
    public  String getToken(String key, Object value);

    Claims getClaims(String token);
}

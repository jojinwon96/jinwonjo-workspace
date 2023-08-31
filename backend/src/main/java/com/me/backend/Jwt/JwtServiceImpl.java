package com.me.backend.Jwt;

import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService{

    private String secretKey = "BulEy1542d13dVBsYt9V7zq57!@MnV%!YcfPQye08f7@#%a1!9X27456541321654!qqkHS";

    @Override
    public String getToken(String key, Object value) {

        // 토큰 유효시간 5분 설정
        Date exapTime = new Date();
        exapTime.setTime(exapTime.getTime() * 1000 * 60 * 5);

        // 랜덤키를 바이트로 변환
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);

        // HS256으로 키 암호화
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(exapTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {
        if (token != null && !"".equals(token)) {
            try {
                byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                // 만료됨
            } catch (JwtException e) {
                // 유효하지 않음
            }
        }

        return null;
    }
}

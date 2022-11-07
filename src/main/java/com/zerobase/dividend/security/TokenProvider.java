package com.zerobase.dividend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;   //1 HOUR

    @Value("{spring.jwt.secret}")
    private String secretKey;

    /**
     * 토큰 생성(발급)
     */
    public String generateToken(String userName, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put(KEY_ROLES, roles);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 토큰 생성 시간
                .setExpiration(expiredDate)     // 토큰 만료 시간
                // 사용할 암호화 알고리즘, 비밀 키
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();

    }

    public String getUserName(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token))
            return false;

        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
            return Jwts.parser().setSigningKey(this.secretKey)
                .parseClaimsJws(token).getBody();
    }
}

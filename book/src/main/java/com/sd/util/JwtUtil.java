package com.sd.util;

import com.sd.dto.LoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

/**
 * @Package: com.sd.util.JwtUtil
 * @Description:
 * @author sudan
 * @date 2020/12/1 16:41
 */

@Data
public class JwtUtil {

    @Value("${jwt.config.key}")
    private String key;
    @Value("${jwt.config.ttl}")
    private long ttl;

    public String createJwt(String id, String subject, LoginDto loginDto){
        long now=System.currentTimeMillis();
        long exp=now+ttl;
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key)
                .claim("loginInfo",loginDto);
        if(ttl>0){
            jwtBuilder.setExpiration(new Date(exp));
        }
        String compact = jwtBuilder.compact();
        return compact;
    }

    public Claims parseToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }


}
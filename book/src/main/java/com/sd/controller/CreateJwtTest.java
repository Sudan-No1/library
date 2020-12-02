package com.sd.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Package: com.sd.controller.CreateJwtTest
 * @Description: 
 * @author sudan
 * @date 2020/12/1 16:12
 */
 
 
public class CreateJwtTest {
    public static void main(String[] args) {
        String token = generateToken();
        Claims claims = Jwts.parser().setSigningKey("sd123").parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        System.out.println(subject);

    }
    private static String generateToken(){
        JwtBuilder jwtBuilder = Jwts.builder().setId("888")
                .setSubject("zhangsan")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "sd123");
       return jwtBuilder.compact();
    }
}
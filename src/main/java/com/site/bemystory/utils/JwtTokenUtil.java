package com.site.bemystory.utils;

import com.site.bemystory.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.util.Date;
@RequiredArgsConstructor
public class JwtTokenUtil{
    public static String  getUserName(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userName", String.class);
    }
    public static boolean isExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                        .getBody().getExpiration().before(new Date());
    }

    public static Long getExpiration(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().getTime();
    }
    public static String createToken(String userName, String key, Long expireTimeMs){
        Claims claims = Jwts.claims();  //일종의 map
        claims.put("userName", userName);   //token 열면 username 들어있음

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact()
                ;
    }

}

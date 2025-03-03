package com.brick.codesnippetbackend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class JwtUtils
{

    private SecretKey tokenSignKey; // JWT加密或解密的秘钥
    private long expirationTime;

    @Autowired
    public JwtUtils(@Value("${token.secretKey}") String secretKey, @Value("${token.expireTime}") long expirationTime)
    {
        this.tokenSignKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationTime = expirationTime;
    }


    // Generated token in there
    public String generateToken(String id)
    {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, id);
    }

    private String createToken(Map<String, Object> claims, String subject)
    {

        return Jwts.builder()
                .setClaims(claims)   // 载荷-标准中注册的声明
                .setSubject(subject) // 设置主题
                .setIssuedAt(new Date(System.currentTimeMillis()))  //设置 JWT 的签发时间为当前时间
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))   //设置过期时间
                .signWith(tokenSignKey, SignatureAlgorithm.HS256) //使用指定的签名算法（这里是 HS256）和密钥（secretKey）对 JWT 进行签名
                .compressWith(CompressionCodecs.GZIP) //使用 GZIP 压缩 JWT，以减少其大小，便于传输。
                .compact();  //构建和返回
    }

    // 验证JWT
    public boolean validateToken(String token, String id)
    {
        final String extractId = extractId(token);
        return (extractId.equals(id) && isTokenExpired(token));
    }

    // 从JWT中提取用户Id
    public String extractId(String token)
    {
        return extractAllClaims(token).getSubject();
    }

    // 检查JWT是否过期
    public boolean isTokenExpired(String token)
    {
        return !extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // 提取所有声明
    private Claims extractAllClaims(String token) throws ExpiredJwtException
    {
        return Jwts.parserBuilder()
                .setSigningKey(tokenSignKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
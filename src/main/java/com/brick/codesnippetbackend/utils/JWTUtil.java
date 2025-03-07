package com.brick.codesnippetbackend.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JWTUtil implements JWTCustomInterface
{
    private String KEY;
    private Integer EXPIRE_TIME;

    @Autowired
    public JWTUtil(@Value("${token.secretKey}") String KEY, @Value("${token.expireTime}") Integer EXPIRE_TIME)
    {
        this.KEY = KEY;
        this.EXPIRE_TIME = EXPIRE_TIME;

        log.info("KEY: {}, EXPIRE_TIME: {}", KEY, EXPIRE_TIME);
    }

    @Override
    public String createToken(String subject)
    {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.MILLISECOND, this.EXPIRE_TIME);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);

        String token = cn.hutool.jwt.JWTUtil.createToken(payload, KEY.getBytes());
        log.info("生成JWT token：{}", token);
        return token;
    }


    @Override
    public boolean isExpired(String token)
    {
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(token).setKey(KEY.getBytes());
        try
        {
            return !jwt.validate(0); // 验证通过就是没过期，取反就是not expired
        } catch (Exception e)
        {
            log.info("解析遇到问题 JWT失效");
            return true;
        }
    }

    @Override
    public String extractToken(String token)
    {
        return cn.hutool.jwt.JWTUtil.parseToken(token)
                .setKey(KEY.getBytes())
                .getPayload()
                .getClaimsJson()
                .toString();
    }
}

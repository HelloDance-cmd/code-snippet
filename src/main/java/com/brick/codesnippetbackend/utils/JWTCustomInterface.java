package com.brick.codesnippetbackend.utils;

public interface JWTCustomInterface
{
    String createToken(String subject);

    boolean isExpired(String token);

    String extractToken(String token);
}

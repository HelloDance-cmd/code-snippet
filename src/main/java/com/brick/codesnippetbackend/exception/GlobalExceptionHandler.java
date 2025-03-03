package com.brick.codesnippetbackend.exception;

import com.brick.codesnippetbackend.vo.Result;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Result<String>> entity()
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new Result<>(HttpStatus.UNAUTHORIZED.ordinal(), "Expired JWT token"));
    }

}

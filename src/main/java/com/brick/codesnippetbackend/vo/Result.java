package com.brick.codesnippetbackend.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Result<T>
{
    @NotNull
    private int code;
    @NotNull
    @NotBlank
    private T data;

    public static <T> HttpEntity<Result<T>> bus(HttpStatus code, T msg)
    {
        return ResponseEntity.status(code).body(new Result<T>(code.ordinal(), msg));
    }

    public static HttpEntity<Result<String>> bus()
    {
        return ResponseEntity.status(HttpStatus.OK).body(new Result<String>(HttpStatus.OK.ordinal(), "Success"));
    }
}

package com.brick.codesnippetbackend;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
@MapperScan(basePackages = {"com.brick.codesnippetbackend.mapper"})
public class CodeSnippetBackendApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CodeSnippetBackendApplication.class, args);
        log.info("Code Snippet Backend Application Started");
    }
}

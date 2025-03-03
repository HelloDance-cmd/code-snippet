package com.brick.codesnippetbackend.controller;


import com.brick.codesnippetbackend.utils.JwtUtils;
import com.brick.codesnippetbackend.vo.Result;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@Slf4j
@NoArgsConstructor
public class UserController
{
    private JwtUtils jwtUtils;

    @Autowired
    UserController(JwtUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password)
    {
        log.info("Login Username {}: Password {}", username, password);
        return jwtUtils.generateToken(username);
    }

    @GetMapping("/dashboard")
    public HttpEntity<Result<String>> dashboard()
    {
        log.info("dashboard");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new Result<>(HttpStatus.ACCEPTED.ordinal(), "success"));
    }
}

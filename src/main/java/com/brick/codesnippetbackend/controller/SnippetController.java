package com.brick.codesnippetbackend.controller;

import com.brick.codesnippetbackend.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/snippet")
public class SnippetController
{
    private final JwtUtils jwtUtils;

    @Autowired
    SnippetController(JwtUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/get")
    public String getSnippet(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        String username = jwtUtils.extractId(token); // token id is a username

        return String.format("{\"username\":\"%s\"}", username);
    }

}

package com.brick.codesnippetbackend.controller;


import com.brick.codesnippetbackend.service.impl.SnippetsServiceImpl;
import com.brick.codesnippetbackend.service.impl.UsersServiceImpl;
import com.brick.codesnippetbackend.utils.JWTUtil;
import com.brick.codesnippetbackend.vo.RegisterVo;
import com.brick.codesnippetbackend.vo.Result;
import com.brick.codesnippetbackend.vo.SnippetsVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码片段管理面板
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController
{
    private final JWTUtil jwtUtil;
    private final SnippetsServiceImpl snippetsService;

    public DashboardController(JWTUtil jwtUtil, SnippetsServiceImpl snippetsService)
    {
        this.jwtUtil = jwtUtil;
        this.snippetsService = snippetsService;
    }

    @RequestMapping("/getFolderStructure")
    public HttpEntity<Result<List<SnippetsVo>>> getFolderStructure(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        String userName = jwtUtil.extractToken(token);
        List<SnippetsVo> fileStructure = snippetsService.getFileStructure(userName);

        return Result.bus(HttpStatus.OK, fileStructure);
    }
}

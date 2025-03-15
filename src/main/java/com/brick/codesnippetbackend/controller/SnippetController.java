package com.brick.codesnippetbackend.controller;

import com.brick.codesnippetbackend.service.SnippetsService;
import com.brick.codesnippetbackend.service.UsersService;
import com.brick.codesnippetbackend.utils.JWTUtil;
import com.brick.codesnippetbackend.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码片段管理
 */
@RestController
@RequestMapping("/snippet")
public class SnippetController {
	private final JWTUtil jwtUtil;
	private final SnippetsService snippetsService;

	public SnippetController(JWTUtil jwtUtil, SnippetsService snippetsService) {
		this.jwtUtil = jwtUtil;
		this.snippetsService = snippetsService;
	}

	@PostMapping("/directories")
	public HttpEntity<Result<List<String>>> getDirectories(HttpServletRequest request) {
		String token = request.getHeader("token");
		String username = jwtUtil.extractToken(token);

		List<String> directories = snippetsService.getDirectories(username);
		return Result.bus(HttpStatus.OK, directories);
	}
}

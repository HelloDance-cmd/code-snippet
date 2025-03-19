package com.brick.codesnippetbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brick.codesnippetbackend.dto.DirectoryDto;
import com.brick.codesnippetbackend.entity.SnippetTags;
import com.brick.codesnippetbackend.entity.Tags;
import com.brick.codesnippetbackend.entity.Users;
import com.brick.codesnippetbackend.service.SnippetTagsService;
import com.brick.codesnippetbackend.service.SnippetsService;
import com.brick.codesnippetbackend.service.TagsService;
import com.brick.codesnippetbackend.service.UsersService;
import com.brick.codesnippetbackend.utils.JWTUtil;
import com.brick.codesnippetbackend.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代码片段管理
 */
@RestController
@RequestMapping("/snippet")
public class SnippetController {
	private final JWTUtil jwtUtil;
	private final SnippetsService snippetsService;
	private final SnippetTagsService snippetTagsService;
	private final UsersService usersService;
	private final TagsService tagsService;
	private final Result result;

	public SnippetController(JWTUtil jwtUtil, SnippetsService snippetsService, SnippetTagsService snippetTagsService, UsersService usersService, TagsService tagsService, Result result) {
		this.jwtUtil = jwtUtil;
		this.snippetsService = snippetsService;
		this.snippetTagsService = snippetTagsService;
		this.usersService = usersService;
		this.tagsService = tagsService;
		this.result = result;
	}

	/**
	 * 获取所有的目录
	 *
	 * @return 目录
	 */
	@PostMapping("/directories")
	public HttpEntity<Result<List<DirectoryDto>>> getDirectories(HttpServletRequest request) {
		String token = request.getHeader("token");
		String username = jwtUtil.extractToken(token);

		List<DirectoryDto> directories = snippetsService.getDirectories(username);
		return Result.bus(HttpStatus.OK, directories);
	}

	/**
	 * 通过ID移除对应的snippet
	 *
	 * @param id id
	 * @return 移除是否成功
	 */
	@GetMapping("/remove")
	public HttpEntity<Result<Boolean>> removeSnippet(@RequestParam String id) {
		return Result.bus(HttpStatus.OK, snippetsService.removeById(id));
	}

	/**
	 * 获取snippet中tag出现的频率
	 */
	@GetMapping("/frequent")
	public HttpEntity<Result<List<List<Object>>>> getFrequent(HttpServletRequest request) {
		String token = request.getHeader("token");
		String username = jwtUtil.extractToken(token);
		Integer userId = usersService.queryUserIdByUsername(username);

		List<SnippetTags> snippetTags = snippetTagsService.list(new QueryWrapper<SnippetTags>().eq("user_id", userId));
		Map<String, Object> tagsMap = tagsService.getMap(new QueryWrapper<>());

		Map<String, Integer> counter = new HashMap<>();
		for (SnippetTags snippetTag : snippetTags) {
			counter.computeIfPresent(snippetTag.getSnippetId(), (k, v) -> v + 1);
			counter.computeIfAbsent(snippetTag.getSnippetId(), k -> 1);
		}

		List<List<Object>> list = counter.entrySet()
			.stream()
			.map(entry -> List.of(tagsMap.get(entry.getKey()), entry.getValue()))
			.toList();

		return Result.bus(HttpStatus.OK, list);
	}
}

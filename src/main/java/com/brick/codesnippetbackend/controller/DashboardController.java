package com.brick.codesnippetbackend.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.brick.codesnippetbackend.dto.MultipleSnippetUploadDto;
import com.brick.codesnippetbackend.dto.UpdateOneSnippetDto;
import com.brick.codesnippetbackend.entity.Snippets;
import com.brick.codesnippetbackend.service.impl.SnippetsServiceImpl;
import com.brick.codesnippetbackend.utils.JWTUtil;
import com.brick.codesnippetbackend.vo.Result;
import com.brick.codesnippetbackend.vo.SnippetsVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码片段管理面板
 */
@Slf4j
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	private final JWTUtil jwtUtil;
	private final SnippetsServiceImpl snippetsService;

	public DashboardController(JWTUtil jwtUtil, SnippetsServiceImpl snippetsService) {
		this.jwtUtil = jwtUtil;
		this.snippetsService = snippetsService;
	}

	/**
	 * 获取文件结构
	 * 当用户查看自身所拥有的片段的时候的时候
	 * 通过该用户创建的文件夹结构，最顶层的用户的content应该是文件夹名称而不是文件内容，只有children属性才会有content内容
	 *
	 * @param request 请求体
	 * @return 文件结构
	 */
	@GetMapping("/get-folder-structure")
	public HttpEntity<Result<List<SnippetsVo>>> getFolderStructure(HttpServletRequest request) {
		log.info("get-folder-structure");
		String token = request.getHeader("token");
		String userName = jwtUtil.extractToken(token);

		log.info("userName apply a file structure: {}", userName);
		List<SnippetsVo> fileStructure = snippetsService.getFileStructure(userName);

		log.info("getFolderStructure: fileStructure= {} ", fileStructure);
		return Result.bus(HttpStatus.OK, fileStructure);
	}

	/**
	 * 单片段更新
	 * 当用户做出更新行为的时候
	 * 更新单条记录，这个记录已经在表中出现过
	 *
	 * @param updateOneSnippetDto 更新表所需要的必要信息
	 * @return 是否更新成功
	 */
	@PostMapping("/snippetContentUpdate")
	public HttpEntity<Result<Boolean>> snippetContentUpdate(@RequestBody UpdateOneSnippetDto updateOneSnippetDto) {
		log.info("fileContentUpdate, {}", updateOneSnippetDto);

		boolean updated = snippetsService.update(new UpdateWrapper<Snippets>()
			.eq("id", updateOneSnippetDto.getId())
			.set("content", updateOneSnippetDto.getContent()));

		return Result.bus(updated ? HttpStatus.OK : HttpStatus.NOT_EXTENDED, updated);
	}


	/**
	 * 多文件上传
	 * 当用户新建文件或者目录的时候
	 * 向snippet表中添加数据
	 *
	 * @param uploadDto 新建的数据集包含文件和目录
	 * @return 是否成功
	 */
	@PostMapping("/multipleSnippetUpload")
	public HttpEntity<Result<Boolean>> multipleSnippetUpload(@RequestBody MultipleSnippetUploadDto uploadDto,
	                                                         HttpServletRequest request) {
		log.info("multipleSnippetUpload, {}", uploadDto);

		boolean isSuccess = snippetsService.insertSnippet(uploadDto, request.getHeader("token"));
		return Result.bus(isSuccess ? HttpStatus.OK : HttpStatus.NOT_EXTENDED, isSuccess);
	}
}

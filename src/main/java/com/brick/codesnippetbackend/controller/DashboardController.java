package com.brick.codesnippetbackend.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.brick.codesnippetbackend.dto.SnippetUpdateDto;
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
public class DashboardController
{
    private final JWTUtil jwtUtil;
    private final SnippetsServiceImpl snippetsService;

    public DashboardController(JWTUtil jwtUtil, SnippetsServiceImpl snippetsService)
    {
        this.jwtUtil = jwtUtil;
        this.snippetsService = snippetsService;
    }

    /**
     * 获取文件结构
     * 通过该用户创建的文件夹结构，最顶层的用户的content应该是文件夹名称而不是文件内容，只有children属性才会有content内容
     *
     * @param request 请求体
     * @return 文件结构
     */
    @GetMapping("/get-folder-structure")
    public HttpEntity<Result<List<SnippetsVo>>> getFolderStructure(HttpServletRequest request)
    {
        log.info("get-folder-structure");
        String token = request.getHeader("token");
        String userName = jwtUtil.extractToken(token);

        log.info("userName apply a file structure: {}", userName);
        List<SnippetsVo> fileStructure = snippetsService.getFileStructure(userName);

        log.info("getFolderStructure: fileStructure= {} ", fileStructure);
        return Result.bus(HttpStatus.OK, fileStructure);
    }

    @PostMapping("/snippetContentUpdate")
    public HttpEntity<Result<Boolean>> snippetContentUpdate(@RequestBody SnippetUpdateDto snippetUpdateDto) {
        log.info("fileContentUpdate, {}", snippetUpdateDto);

        boolean updated = snippetsService.update(new UpdateWrapper<Snippets>()
                .eq("id", snippetUpdateDto.getId())
                .set("content", snippetUpdateDto.getContent()));

        return Result.bus(updated ? HttpStatus.OK : HttpStatus.NOT_EXTENDED, updated);
    }
}

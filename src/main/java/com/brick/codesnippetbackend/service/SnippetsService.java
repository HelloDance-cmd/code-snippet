package com.brick.codesnippetbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brick.codesnippetbackend.dto.SimpleSnippet;
import com.brick.codesnippetbackend.entity.Snippets;
import com.brick.codesnippetbackend.vo.SnippetsVo;
import lombok.NonNull;

import java.util.List;

/**
 * @author wu-ji
 * @description 针对表【snippets】的数据库操作Service
 * @createDate 2025-03-02 16:47:50
 */
public interface SnippetsService extends IService<Snippets> {
	List<SnippetsVo> getFileStructure(String userName);

	Boolean insertSnippet(SimpleSnippet simpleSnippet, String userToken);

	List<String> getDirectories(@NonNull String username);
}

package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brick.codesnippetbackend.dto.DirectoryDto;
import com.brick.codesnippetbackend.dto.SimpleSnippet;
import com.brick.codesnippetbackend.entity.Snippets;
import com.brick.codesnippetbackend.entity.Users;
import com.brick.codesnippetbackend.mapper.SnippetsMapper;
import com.brick.codesnippetbackend.mapper.UsersMapper;
import com.brick.codesnippetbackend.service.SnippetsService;
import com.brick.codesnippetbackend.utils.JWTUtil;
import com.brick.codesnippetbackend.utils.SnowflakeIdWorker;
import com.brick.codesnippetbackend.vo.SnippetsVo;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author wu-ji
 * @description 针对表【snippets】的数据库操作Service实现
 * @createDate 2025-03-02 16:47:50
 */
@Service
public class SnippetsServiceImpl extends ServiceImpl<SnippetsMapper, Snippets>
	implements SnippetsService {
	private final UsersMapper usersMapper;
	private final SnippetsMapper snippetsMapper;
	private final JWTUtil jwtUtil;
	private final SnowflakeIdWorker snowflakeIdWorker;

	public SnippetsServiceImpl(UsersMapper usersMapper, SnippetsMapper snippetsMapper,
	                           JWTUtil jwtUtil, SnowflakeIdWorker snowflakeIdWorker) {
		this.usersMapper = usersMapper;
		this.snippetsMapper = snippetsMapper;
		this.jwtUtil = jwtUtil;
		this.snowflakeIdWorker = snowflakeIdWorker;
	}

	@Override
	public List<SnippetsVo> getFileStructure(String userName) {
		Integer userId = usersMapper.queryUserIdByUsername(userName);
		List<Snippets> snippets = snippetsMapper.selectList(new QueryWrapper<Snippets>().eq("user_id", userId));
		return adjustStructure(snippets, null);
	}


	@Override
	public List<DirectoryDto> getDirectories(@NonNull String username) {
		// It like following same
		// [ xxx, xxx, children: []]
		List<SnippetsVo> snippets = getFileStructure(username);
		List<DirectoryDto> ans = new ArrayList<>();

		Stack<List<SnippetsVo>> stack = new Stack<>();
		stack.push(snippets);

		// please see it as a function
		while (!stack.empty()) {
			// As the structure of it like a functional entry
			// so this be called 'parameter'
			List<SnippetsVo> snippetsParam = stack.pop();

			for (SnippetsVo snippetsVo : snippetsParam) {
				if (!snippetsVo.getChildren().isEmpty()) { // if children is not null, then it be a directory
					DirectoryDto directory = DirectoryDto.builder()
						.id(snippetsVo.getSnippetID())
						.name(snippetsVo.getTitle())
						.build();

					ans.add(directory);
					stack.add(snippetsVo.getChildren());
				}
			}
		}

		return ans;
	}

	@Override
	public Boolean insertSnippet(SimpleSnippet simpleSnippet, String userToken) {
		String username = jwtUtil.extractToken(userToken);
		Integer userId = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username)).getId();

		Snippets snippetsEntity = new Snippets();
		BeanUtils.copyProperties(simpleSnippet, snippetsEntity);
		snippetsEntity.setContent("");
		String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		snippetsEntity.setCreatedAt(Date.valueOf(nowDate));
		snippetsEntity.setUserId(userId);
		snippetsEntity.setId(snowflakeIdWorker.nextIdStr());

		return snippetsMapper.insertOrUpdate(snippetsEntity);
	}

	private List<SnippetsVo> adjustStructure(List<Snippets> snippets, String parentId) {
		List<SnippetsVo> ans = new ArrayList<>();
		for (Snippets snippet : snippets) {
			String PId = snippet.getParentId();
			if (PId != null && PId.equals(parentId) || PId == null && parentId == null) {
				SnippetsVo snippetsVo = toSnippetVo(snippet);
				snippetsVo.setChildren(adjustStructure(snippets, snippet.getId()));
				ans.add(snippetsVo);
			}
		}
		return ans;
	}

	public SnippetsVo toSnippetVo(Snippets snippet) {
		SnippetsVo snippetsVo = new SnippetsVo();
		snippetsVo.setCreateAt(snippet.getCreatedAt());
		snippetsVo.setChildren(List.of());
		snippetsVo.setTitle(snippet.getTitle());
		snippetsVo.setSnippetID(snippet.getId());
		snippetsVo.setContent(snippet.getContent());
		snippetsVo.setCategory(snippet.getLanguage().toString());

		return snippetsVo;
	}

}





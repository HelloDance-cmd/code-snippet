package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brick.codesnippetbackend.dto.MultipleSnippetUploadDto;
import com.brick.codesnippetbackend.entity.Snippets;
import com.brick.codesnippetbackend.entity.Users;
import com.brick.codesnippetbackend.mapper.SnippetsMapper;
import com.brick.codesnippetbackend.mapper.UsersMapper;
import com.brick.codesnippetbackend.service.SnippetsService;
import com.brick.codesnippetbackend.utils.JWTUtil;
import com.brick.codesnippetbackend.vo.SnippetsVo;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public SnippetsServiceImpl(UsersMapper usersMapper, SnippetsMapper snippetsMapper, JWTUtil jwtUtil) {
		this.usersMapper = usersMapper;
		this.snippetsMapper = snippetsMapper;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public List<SnippetsVo> getFileStructure(String userName) {
		Integer userId = usersMapper.queryUserIdByUsername(userName);
		List<Snippets> snippets = snippetsMapper.selectList(new QueryWrapper<Snippets>().eq("user_id", userId));
		return adjustStructure(snippets, null);
	}

	/**
	 * 当用户新建文件或者目录的时候
	 * 向snippet表中添加数据
	 *
	 * @param uploadDto 新建的数据集包含文件和目录
	 * @return 是否成功
	 */
	@Override
	public Boolean insertSnippet(MultipleSnippetUploadDto uploadDto, String userToken) {
		String username = jwtUtil.extractToken(userToken);
		Integer userId = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username)).getId();

		List<Snippets> snippets = Arrays.stream(uploadDto.getSnippets())
			.map(snippet -> {
				Snippets snippetsEntity = new Snippets();
				BeanUtils.copyProperties(snippet, snippetsEntity);
				snippetsEntity.setCreatedAt(LocalTime.now());
				snippetsEntity.setUserId(userId);
				return snippetsEntity;
			}).toList();

		int affectRows = snippetsMapper.insertOrUpdate(snippets).size();
		return affectRows == uploadDto.getSnippets().length;
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





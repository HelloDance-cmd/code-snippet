package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brick.codesnippetbackend.entity.Snippets;
import com.brick.codesnippetbackend.mapper.SnippetsMapper;
import com.brick.codesnippetbackend.mapper.UsersMapper;
import com.brick.codesnippetbackend.service.SnippetsService;
import com.brick.codesnippetbackend.vo.SnippetsVo;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wu-ji
 * @description 针对表【snippets】的数据库操作Service实现
 * @createDate 2025-03-02 16:47:50
 */
@Service
public class SnippetsServiceImpl extends ServiceImpl<SnippetsMapper, Snippets>
        implements SnippetsService
{
    private final UsersMapper usersMapper;
    private final SnippetsMapper snippetsMapper;

    public SnippetsServiceImpl(UsersMapper usersMapper, SnippetsMapper snippetsMapper)
    {
        this.usersMapper = usersMapper;
        this.snippetsMapper = snippetsMapper;
    }

    @Override
    public List<SnippetsVo> getFileStructure(String userName)
    {
        Integer userId = usersMapper.queryUserIdByUsername(userName);
        List<Snippets> snippets = snippetsMapper.selectList(new QueryWrapper<Snippets>().eq("user_id", userId));
        return adjustStructure(snippets);
    }

    private List<SnippetsVo> adjustStructure(List<Snippets> snippets)
    {
        List<SnippetsVo> ans = new ArrayList<>();
        Stack<SnippetsVo> stack = new Stack<>();
        HashMap<String, Boolean> map = new HashMap<>();

        for (Snippets snippet : snippets)
        {
            if (map.get(snippet.getId()) != null)
                continue;
            stack.push(toSnippetVo(snippet));
            String parentId = snippet.getParentId();

            // Find an element is parentId = this
            SnippetsVo obj = getParent(parentId, snippets);

            // The element not have a parent element
            if (Objects.isNull(obj))
            {
                SnippetsVo snip = stack.pop();
                ans.add(snip);
            } else
            {
                // The element have many parent
                SnippetsVo first = stack.firstElement();
                while (!stack.empty())
                {
                    SnippetsVo s = stack.pop();
                    map.put(s.getSnippetID(), true);
                    if (!stack.empty())
                    {
                        s.getChildren().add(stack.firstElement());
                    }
                }

                ans.add(first);
            }
        }

        return ans;
    }

    private SnippetsVo getParent(String parentId, List<Snippets> snippets)
    {
        for (Snippets snippet : snippets)
        {
            if (snippet.getId().equals(parentId))
            {
                return toSnippetVo(snippet);

            }
        }

        return null;
    }

    public SnippetsVo toSnippetVo(Snippets snippet)
    {
        SnippetsVo snippetsVo = new SnippetsVo();

        snippetsVo.setChildren(List.of());
        snippetsVo.setTitle(snippet.getTitle());
        snippetsVo.setSnippetID(snippet.getId());
        snippetsVo.setContent(snippet.getContent());
        snippetsVo.setCategory(snippet.getLanguage().toString());

        return snippetsVo;
    }

}





package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brick.codesnippetbackend.entity.Tags;
import com.brick.codesnippetbackend.service.TagsService;
import com.brick.codesnippetbackend.mapper.TagsMapper;
import org.springframework.stereotype.Service;

/**
* @author wu-ji
* @description 针对表【tags】的数据库操作Service实现
* @createDate 2025-03-02 16:47:50
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

}





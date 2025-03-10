package com.brick.codesnippetbackend.mapper;

import com.brick.codesnippetbackend.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author wu-ji
* @description 针对表【users】的数据库操作Mapper
* @createDate 2025-03-02 16:47:50
* @Entity entity.brick.Users
*/
public interface UsersMapper extends BaseMapper<Users> {
    Integer queryUserIdByUsername(String username);
}





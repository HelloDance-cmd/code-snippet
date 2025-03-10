package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brick.codesnippetbackend.entity.UserRoles;
import com.brick.codesnippetbackend.service.UserRolesService;
import com.brick.codesnippetbackend.mapper.UserRolesMapper;
import org.springframework.stereotype.Service;

/**
* @author wu-ji
* @description 针对表【user_roles】的数据库操作Service实现
* @createDate 2025-03-02 16:47:50
*/
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles>
    implements UserRolesService{

}





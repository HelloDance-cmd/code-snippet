package com.brick.codesnippetbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brick.codesnippetbackend.entity.Users;
import com.brick.codesnippetbackend.dto.RegisterDto;
import lombok.NonNull;

import java.util.List;

/**
 * @author wu-ji
 * @description 针对表【users】的数据库操作Service
 * @createDate 2025-03-02 16:47:50
 */
public interface UsersService extends IService<Users>
{
    boolean usernameExists(String username);

    boolean insertUser(@NonNull RegisterDto registerVo);

    boolean emailExists(@NonNull String email);
}

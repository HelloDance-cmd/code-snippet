package com.brick.codesnippetbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brick.codesnippetbackend.dto.RegisterDto;
import com.brick.codesnippetbackend.entity.Users;
import lombok.NonNull;

/**
 * @author wu-ji
 * @description 针对表【users】的数据库操作Service
 * @createDate 2025-03-02 16:47:50
 */
public interface UsersService extends IService<Users> {
	/**
	 * 用户是否存在
	 * @param username 用户名
	 * @return 是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 增加一个用户
	 * @param registerVo 注册表单
	 * @return 插入成功
	 */
	boolean insertUser(@NonNull RegisterDto registerVo);

	/**
	 * 判断邮箱是否存在
	 *
	 * @param email
	 * @return
	 */
	boolean emailExists(@NonNull String email);

	/**
	 * 记录用户登录信息
	 */
	void recordUserLoginInfo(String username);

	/**
	 * 通过用户名获取Id
	 * @param username
	 * @return
	 */
	Integer queryUserIdByUsername(@NonNull String username);
}

package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brick.codesnippetbackend.dto.RegisterDto;
import com.brick.codesnippetbackend.entity.UserLog;
import com.brick.codesnippetbackend.entity.Users;
import com.brick.codesnippetbackend.mapper.UserLogMapper;
import com.brick.codesnippetbackend.mapper.UsersMapper;
import com.brick.codesnippetbackend.service.UsersService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author wu-ji
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2025-03-02 16:47:50
 */
@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
	implements UsersService {
	private final UsersMapper usersMapper;
	private final UserLogMapper userLogMapper;

	public UsersServiceImpl(UsersMapper usersMapper, UserLogMapper userLogMapper) {
		this.userLogMapper = userLogMapper;
		this.usersMapper = usersMapper;
	}

	public boolean usernameExists(String username) {
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);

		return exists(queryWrapper);
	}

	@Override
	public boolean emailExists(@NonNull String email) {
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("email", email);

		return exists(queryWrapper);
	}

	@Override
	public void recordUserLoginInfo(String username) {
		Integer userId = usersMapper.queryUserIdByUsername(username);
		if (userId == null) {
			return;
		}

		UserLog userLog = new UserLog();
		userLog.setUserId(userId);
		userLog.setTime(LocalDateTime.now());
		userLog.setStatus(UserLog.Status.LOGIN);
		log.info("Generated UserLog: {}", userLog);
		userLogMapper.insert(userLog);
	}

	@Override
	public Integer queryUserIdByUsername(@NonNull String username) {
		return usersMapper.queryUserIdByUsername(username);
	}

	@Override
	public boolean insertUser(@NonNull RegisterDto registerVo) {
		Users users = Users.builder()
			.username(registerVo.getUsername())
			.passwordHash(registerVo.getPassword())
			.createdAt(LocalDateTime.now())
			.email(registerVo.getEmail())
			.build();

		return baseMapper.insertOrUpdate(users);
	}

}





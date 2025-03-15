package com.brick.codesnippetbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brick.codesnippetbackend.dto.LoginDto;
import com.brick.codesnippetbackend.dto.RegisterDto;
import com.brick.codesnippetbackend.vo.UserInformationVo;
import com.brick.codesnippetbackend.entity.Users;
import com.brick.codesnippetbackend.service.impl.UsersServiceImpl;
import com.brick.codesnippetbackend.utils.JWTUtil;
import com.brick.codesnippetbackend.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 用户模块
 */
@RequestMapping("/user")
@RestController
@Slf4j
@NoArgsConstructor
@CrossOrigin
public class UserController {

	private JWTUtil jwtUtils;
	private UsersServiceImpl usersService;

	@Autowired
	UserController(JWTUtil jwtUtils, UsersServiceImpl usersService) {
		this.jwtUtils = jwtUtils;
		this.usersService = usersService;
	}

	/**
	 * 登录接口
	 *
	 * @param loginDto 登录需要的信息
	 * @return 返回提示信息，登录成功或者是失败。如果成功返回该用户的Token，如果失败返回失败信息
	 */
	@PostMapping("/login")
	public HttpEntity<Result<String>> login(@Valid @RequestBody LoginDto loginDto) {
		String username = loginDto.getUsername();
		String password = loginDto.getPassword();

		if (!usersService.usernameExists(username)) {
			log.warn("User is not exist {}", username);
			return Result.bus(HttpStatus.IM_USED, "This user is Not Exists");
		}
		String passwordHash = usersService.getOne(new QueryWrapper<Users>().eq("username", username)).getPasswordHash();

		log.info("User login {}, hash password: {}", username, passwordHash);

		if (!passwordHash.equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			log.warn("Password hash does not match, password hash: {}", passwordHash);
			return Result.bus(HttpStatus.UNAUTHORIZED, "Password Incorrect");
		}

		log.info("User login successful");
		return Result.bus(HttpStatus.OK, jwtUtils.createToken(username));
	}

	/**
	 * 注册接口
	 *
	 * @param registerVo 注册相关信息
	 * @return 注册成功或者是失败
	 */
	@PostMapping("/register")
	public HttpEntity<Result<String>> dashboard(@RequestBody RegisterDto registerVo) {

		if (usersService.usernameExists(registerVo.getUsername())) {
			log.warn("User is exist {}", registerVo.getUsername());
			return Result.bus(HttpStatus.IM_USED, "User name already used");
		}

		if (usersService.emailExists(registerVo.getEmail())) {
			log.warn("User email is exist {}", registerVo.getEmail());
			return Result.bus(HttpStatus.IM_USED, "User email already used");
		}

		String newPassword = DigestUtils.md5DigestAsHex(registerVo.getPassword().getBytes());
		registerVo.setPassword(newPassword);

		log.info("User register successful");

		if (!usersService.insertUser(registerVo))
			return Result.bus(HttpStatus.BAD_REQUEST, "Registration failed, Encountering unexpected situations");


		return ResponseEntity.status(HttpStatus.OK).body(
			new Result<>(HttpStatus.OK.ordinal(), "success"));
	}

	/**
	 * 判断用户是否过期
	 *
	 * @param token 输入一个用户token
	 * @return 返回是否过期
	 */
	@GetMapping("/isExpired")
	public HttpEntity<Result<Boolean>> isExpired(@RequestParam @NotBlank String token) {

		boolean isExpired = jwtUtils.isExpired(token);
		log.info("Check if expired {}, current status of expired {}", token.substring(0, 10), isExpired);
		return ResponseEntity.status(HttpStatus.OK).body(
			new Result<>(HttpStatus.OK.ordinal(), isExpired));
	}

	@PostMapping("/whoami")
	public HttpEntity<Result<UserInformationVo>> whoami(HttpServletRequest request) {
		String token = request.getHeader("token");
		String username = jwtUtils.extractToken(token);
		String email = usersService.getOne(
				new QueryWrapper<Users>().eq("username", username)).getEmail();

		var userInfo = new UserInformationVo();
		userInfo.setEmail(email);
		userInfo.setUsername(username);

		return Result.bus(HttpStatus.OK, userInfo);
	}
}

package com.brick.codesnippetbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brick.codesnippetbackend.entity.Users;
import com.brick.codesnippetbackend.service.impl.UsersServiceImpl;
import com.brick.codesnippetbackend.utils.JwtUtils;
import com.brick.codesnippetbackend.vo.LoginVo;
import com.brick.codesnippetbackend.vo.RegisterVo;
import com.brick.codesnippetbackend.vo.Result;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@Slf4j
@NoArgsConstructor
@CrossOrigin
public class UserController
{

    private JwtUtils jwtUtils;
    private UsersServiceImpl usersService;

    @Autowired
    UserController(JwtUtils jwtUtils, UsersServiceImpl usersService)
    {
        this.jwtUtils = jwtUtils;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public HttpEntity<Result<String>> login(@RequestBody LoginVo loginVo)
    {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();

        if (!usersService.usernameExists(username))
        {
            log.warn("User is not exist {}", username);
            return ResponseEntity.status(HttpStatus.IM_USED).body(
                    new Result<>(HttpStatus.IM_USED.ordinal(), "This user is Not Exists"));
        }
        String passwordHash = usersService.getOne(new QueryWrapper<Users>().eq("username", username)).getPasswordHash();

        log.info("User login {}, hash password: {}", username, passwordHash);

        if (!passwordHash.equals(DigestUtils.md5DigestAsHex(password.getBytes())))
        {
            log.warn("Password hash does not match, password hash: {}", passwordHash);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Result<>(HttpStatus.UNAUTHORIZED.ordinal(), "Password Incorrect"));
        }

        log.info("User login successful");
        return ResponseEntity.status(HttpStatus.OK).body(
                new Result<>(HttpStatus.OK.ordinal(), jwtUtils.generateToken(username)));
    }

    @PostMapping("/register")
    public HttpEntity<Result<String>> dashboard(@RequestBody @NonNull RegisterVo registerVo)
    {

        if (usersService.usernameExists(registerVo.getUsername()))
        {
            log.warn("User is exist {}", registerVo.getUsername());
            return ResponseEntity.status(HttpStatus.IM_USED).body(
                    new Result<>(HttpStatus.IM_USED.ordinal(), "User name is used"));
        }

        if (usersService.emailExists(registerVo.getEmail()))
        {
            log.warn("User email is exist {}", registerVo.getEmail());
            return ResponseEntity.status(HttpStatus.IM_USED).body(
                    new Result<>(HttpStatus.IM_USED.ordinal(), "User email is used"));
        }

        String newPassword = DigestUtils.md5DigestAsHex(registerVo.getPassword().getBytes());
        registerVo.setPassword(newPassword);

        log.info("User register successful");

        if (!usersService.insertUser(registerVo))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Result<>(HttpStatus.BAD_GATEWAY.ordinal(), "Registration failed, Encountering unexpected situations"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new Result<>(HttpStatus.OK.ordinal(), "success"));
    }
}

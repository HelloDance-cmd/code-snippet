package com.brick.codesnippetbackend.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class RegisterVo
{
    @NotNull
    private String username;
    @NotNull
    private String password;

    @Null
    @Email(message = "邮箱格式不正确")
    private String email;
}

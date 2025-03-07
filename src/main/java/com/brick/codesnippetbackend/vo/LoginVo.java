package com.brick.codesnippetbackend.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginVo
{
    @NotNull
    private String username;
    @NotNull
    private String password;
}

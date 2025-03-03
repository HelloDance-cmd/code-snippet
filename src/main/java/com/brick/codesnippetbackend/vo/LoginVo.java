package com.brick.codesnippetbackend.vo;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginVo
{
    @NonNull private String username;
    @NonNull private String password;
}

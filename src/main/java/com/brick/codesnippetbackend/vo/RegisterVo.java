package com.brick.codesnippetbackend.vo;

import lombok.Data;
import lombok.NonNull;

@Data
public class RegisterVo
{
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
}

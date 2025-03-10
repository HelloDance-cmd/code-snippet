package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @TableName users
 */
@TableName(value ="users")
@Data
@Builder
public class Users {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String email;

    private String passwordHash;

    private LocalDateTime createdAt;
}
package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName user_roles
 */
@TableName(value ="user_roles")
@Data
public class UserRoles {
    private Integer userId;

    private Integer roleId;
}
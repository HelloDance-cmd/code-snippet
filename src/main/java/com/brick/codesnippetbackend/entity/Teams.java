package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName teams
 */
@TableName(value ="teams")
@Data
public class Teams {
    private Integer id;

    private String name;

    private Integer ownerId;

    private Integer quota;
}
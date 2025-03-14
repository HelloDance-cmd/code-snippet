package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

/**
 * @TableName snippets
 */
@TableName(value ="snippets")
@Data
public class Snippets {
    private String id;

    private Integer userId;

    private String title;

    private String content;

    private String language;

    private Date createdAt;

    private String parentId;
}
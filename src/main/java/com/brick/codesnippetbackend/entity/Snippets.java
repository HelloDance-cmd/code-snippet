package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
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

    private Object language;

    private Date createdAt;

    private String parentId;
}
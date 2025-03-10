package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName snippet_tags
 */
@TableName(value ="snippet_tags")
@Data
public class SnippetTags {
    private String snippetId;

    private Integer tagId;
}
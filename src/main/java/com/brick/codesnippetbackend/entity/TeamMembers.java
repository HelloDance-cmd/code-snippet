package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName team_members
 */
@TableName(value ="team_members")
@Data
public class TeamMembers {
    private Integer teamId;

    private Integer userId;

    private Object accessLevel;
}
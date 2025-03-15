package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName user_log
 */
@TableName(value ="user_log")
@Data
public class UserLog {
    private Integer userId;
    private Date loginTime;
}
package com.brick.codesnippetbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.junit.Test;

/**
 * @TableName user_log
 */
@TableName(value ="user_log")
@Data
public class UserLog {
    private Integer userId;
    private LocalDateTime time;
    private Status status;

    public enum Status {
        LOGIN, LOGOUT
    }
}
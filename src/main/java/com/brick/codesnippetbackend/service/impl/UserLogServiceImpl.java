package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.brick.UserLog;
import com.brick.codesnippetbackend.service.UserLogService;
import com.brick.codesnippetbackend.mapper.UserLogMapper;
import org.springframework.stereotype.Service;

/**
* @author wu-ji
* @description 针对表【user_log】的数据库操作Service实现
* @createDate 2025-03-15 17:23:22
*/
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog>
    implements UserLogService{

}





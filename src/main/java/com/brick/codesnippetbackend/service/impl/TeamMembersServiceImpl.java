package com.brick.codesnippetbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brick.codesnippetbackend.entity.TeamMembers;
import com.brick.codesnippetbackend.service.TeamMembersService;
import com.brick.codesnippetbackend.mapper.TeamMembersMapper;
import org.springframework.stereotype.Service;

/**
* @author wu-ji
* @description 针对表【team_members】的数据库操作Service实现
* @createDate 2025-03-02 16:47:50
*/
@Service
public class TeamMembersServiceImpl extends ServiceImpl<TeamMembersMapper, TeamMembers>
    implements TeamMembersService{

}





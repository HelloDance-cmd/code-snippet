package com.brick.codesnippetbackend.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserInformationVo {
	private String username;
	private String email;
}

package com.brick.codesnippetbackend.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@Data
public class SnippetsVo {
	@NotNull
	String title;
	@NotNull
	String content;
	@NotNull
	String category;
	@NotNull
	String snippetID;
	@NotNull
	Date createAt;
	@NotNull
	List<SnippetsVo> children;
}

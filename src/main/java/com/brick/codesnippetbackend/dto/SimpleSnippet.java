package com.brick.codesnippetbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SimpleSnippet {
	@NotNull
	private String parentId;
	@NotNull
	private String title;
	/* 如果这个是一个目录的话这个记录是没有必要拥有content的 */
	/**
	 * 是目录的话
	 */
	@Null
	private String content;
	@NotNull
	private Object language;
}

package com.brick.codesnippetbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UpdateOneSnippetDto {
	@NotNull
	private String id;
	@NotNull
	private String content;
}

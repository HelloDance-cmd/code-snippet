package com.brick.codesnippetbackend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SimpleSnippet {
	@Nullable
	private String parentId;
	@NotNull
	private String title;
}

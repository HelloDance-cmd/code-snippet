package com.brick.codesnippetbackend.dto;

import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MultipleSnippetUploadDto {
	@Null
	private SimpleSnippet[] snippets;
}


package com.brick.codesnippetbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectoryDto {
	@NotNull
	private String id;
	@NotNull
	private String name;
}

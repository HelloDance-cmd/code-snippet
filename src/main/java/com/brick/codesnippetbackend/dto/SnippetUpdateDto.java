package com.brick.codesnippetbackend.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SnippetUpdateDto
{
    private String id;
    private String content;
}

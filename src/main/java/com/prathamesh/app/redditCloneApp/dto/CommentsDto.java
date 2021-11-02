package com.prathamesh.app.redditCloneApp.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentsDto {
	private Long id;
	private Long postId;
	private Instant createdDate;
	private String text;
	private String userName;
}

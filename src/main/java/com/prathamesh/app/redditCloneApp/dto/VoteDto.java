package com.prathamesh.app.redditCloneApp.dto;

import com.prathamesh.app.redditCloneApp.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

	private VoteType voteType;
	private Long postId;
}

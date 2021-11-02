package com.prathamesh.app.redditCloneApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prathamesh.app.redditCloneApp.dto.CommentsDto;
import com.prathamesh.app.redditCloneApp.model.Comment;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "text", source = "commentsDto.text")
	@Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
	Comment commentDtoToComment(CommentsDto commentsDto, Post post, User user);

	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentsDto commentToCommentsDto(Comment comment);
}

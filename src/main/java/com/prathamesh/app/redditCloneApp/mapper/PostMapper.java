package com.prathamesh.app.redditCloneApp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.prathamesh.app.redditCloneApp.dto.PostRequest;
import com.prathamesh.app.redditCloneApp.dto.PostResponse;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.Subreddit;
import com.prathamesh.app.redditCloneApp.model.User;
import com.prathamesh.app.redditCloneApp.repository.CommentRepository;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	@Autowired
    private CommentRepository commentRepository;
    
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

	@Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostResponse mapToDto(Post post); 
	
	Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }
  
    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}

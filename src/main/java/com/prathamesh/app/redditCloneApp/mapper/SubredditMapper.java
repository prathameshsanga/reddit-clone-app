package com.prathamesh.app.redditCloneApp.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.prathamesh.app.redditCloneApp.dto.SubredditDto;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.Subreddit;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	
	SubredditMapper INSTANCE = Mappers.getMapper(SubredditMapper.class);
	
	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);

	default Integer mapPosts(List<Post> numberOfPosts) { 
		return numberOfPosts.size();
		} 

	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}

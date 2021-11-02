package com.prathamesh.app.redditCloneApp.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prathamesh.app.redditCloneApp.dto.PostRequest;
import com.prathamesh.app.redditCloneApp.dto.PostResponse;
import com.prathamesh.app.redditCloneApp.exceptions.PostNotFoundException;
import com.prathamesh.app.redditCloneApp.exceptions.SubredditNotFoundException;
import com.prathamesh.app.redditCloneApp.mapper.PostMapper;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.Subreddit;
import com.prathamesh.app.redditCloneApp.model.User;
import com.prathamesh.app.redditCloneApp.repository.PostRepository;
import com.prathamesh.app.redditCloneApp.repository.SubredditRepository;
import com.prathamesh.app.redditCloneApp.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
	
	private final PostRepository postRepository; 
	private final AuthService authService;
	private final SubredditRepository subredditRepository;
	private final PostMapper postMapper;
	private final UserRepository userRepository;
	
	public Post save(PostRequest requestBody) {
		Subreddit subreddit = subredditRepository.findByName(requestBody.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(requestBody.getSubredditName()));
		
		User currentUser = authService.getCurrentUser();
		
		return postRepository.save(postMapper.map(requestBody, subreddit, currentUser));
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id.toString()));
		PostResponse postResponse = postMapper.mapToDto(post);
		return postResponse;
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream()
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts
        		.stream()
        		.map(postMapper::mapToDto)
        		.collect(Collectors.toList());
    }
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username){
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user)
				.stream()
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
}

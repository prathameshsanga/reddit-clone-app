package com.prathamesh.app.redditCloneApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prathamesh.app.redditCloneApp.dto.PostRequest;
import com.prathamesh.app.redditCloneApp.dto.PostResponse;
import com.prathamesh.app.redditCloneApp.service.PostService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest requestBody) {
		postService.save(requestBody);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return 	ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}
	
	@GetMapping()
	public ResponseEntity<List<PostResponse>> getAllPosts(){
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}
	
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id){
		return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username){
		return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}
}

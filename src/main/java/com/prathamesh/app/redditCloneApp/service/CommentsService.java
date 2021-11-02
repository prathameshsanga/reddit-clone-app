package com.prathamesh.app.redditCloneApp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prathamesh.app.redditCloneApp.dto.CommentsDto;
import com.prathamesh.app.redditCloneApp.exceptions.PostNotFoundException;
import com.prathamesh.app.redditCloneApp.mapper.CommentMapper;
import com.prathamesh.app.redditCloneApp.model.Comment;
import com.prathamesh.app.redditCloneApp.model.NotificationEmail;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.User;
import com.prathamesh.app.redditCloneApp.repository.CommentRepository;
import com.prathamesh.app.redditCloneApp.repository.PostRepository;
import com.prathamesh.app.redditCloneApp.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {

	private final CommentRepository commentRepository;
	private final AuthService authService;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final CommentMapper commentMapper;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	
	public void save(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post Not Found with this id:"+ commentsDto.getPostId().toString()));
		
		Comment comment = commentMapper.commentDtoToComment(commentsDto, post, authService.getCurrentUser()); 
		
		commentRepository.save(comment);	
		
		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post. ");
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername() + "Commented om your post ", user.getEmail(), message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(()-> new PostNotFoundException("Post Not Found with this id:" + postId));
		
		return commentRepository.findByPost(post)
				.stream()
		.map(commentMapper :: commentToCommentsDto)
		.collect(Collectors.toList());
	}

	public List<CommentsDto> getAllCommentsForUser(String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(()-> new UsernameNotFoundException("username not found with this user:"+userName));
		
		List<CommentsDto> commentsDtos = commentRepository.findAllByUser(user)
				.stream()
		.map(commentMapper :: commentToCommentsDto)
		.collect(Collectors.toList());
		//System.out.print(commentsDtos);
		return commentsDtos;
	}
}

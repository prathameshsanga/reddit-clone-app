package com.prathamesh.app.redditCloneApp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prathamesh.app.redditCloneApp.dto.VoteDto;
import com.prathamesh.app.redditCloneApp.exceptions.PostNotFoundException;
import com.prathamesh.app.redditCloneApp.exceptions.SpringRedditException;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.Vote;
import com.prathamesh.app.redditCloneApp.repository.PostRepository;
import com.prathamesh.app.redditCloneApp.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.prathamesh.app.redditCloneApp.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
	 private final VoteRepository voteRepository;
	    private final PostRepository postRepository;
	    private final AuthService authService;

	    @Transactional
	    public void vote(VoteDto voteDto) {
	        Post post = postRepository.findById(voteDto.getPostId())
	                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
	        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
	        if (voteByPostAndUser.isPresent() &&
	                voteByPostAndUser.get().getVoteType()
	                        .equals(voteDto.getVoteType())) {
	            throw new SpringRedditException("You have already "
	                    + voteDto.getVoteType() + "'d for this post");
	        }
	        if (UPVOTE.equals(voteDto.getVoteType())) {
	            post.setVoteCount(post.getVoteCount() + 1);
	        } else {
	            post.setVoteCount(post.getVoteCount() - 1);
	        }
	        voteRepository.save(mapToVote(voteDto, post));
	        postRepository.save(post);
	    }

	    private Vote mapToVote(VoteDto voteDto, Post post) {
	        return Vote.builder()
	                .voteType(voteDto.getVoteType())
	                .post(post)
	                .user(authService.getCurrentUser())
	                .build();
	    }
}

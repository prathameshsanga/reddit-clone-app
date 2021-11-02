package com.prathamesh.app.redditCloneApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.User;
import com.prathamesh.app.redditCloneApp.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}

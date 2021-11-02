package com.prathamesh.app.redditCloneApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prathamesh.app.redditCloneApp.dto.PostResponse;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.Subreddit;
import com.prathamesh.app.redditCloneApp.model.User;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findByUser(User user);

	List<Post> findAllBySubreddit(Subreddit subreddit);

}

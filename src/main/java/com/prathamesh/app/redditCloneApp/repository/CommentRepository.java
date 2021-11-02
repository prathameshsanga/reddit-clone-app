package com.prathamesh.app.redditCloneApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prathamesh.app.redditCloneApp.model.Comment;
import com.prathamesh.app.redditCloneApp.model.Post;
import com.prathamesh.app.redditCloneApp.model.User;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);

}

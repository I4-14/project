package com.sparta.trello.comment.repository;

import com.sparta.trello.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}

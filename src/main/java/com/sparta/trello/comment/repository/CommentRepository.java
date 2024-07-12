package com.sparta.trello.comment.repository;

import com.sparta.trello.comment.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

  Page<List<Comment>> findByCardIdOrderByCreatedAtDesc(Long cardId, Pageable pageable);
}

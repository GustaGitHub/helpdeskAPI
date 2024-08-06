package com.enterprise.helpdeskAPI.repository;

import com.enterprise.helpdeskAPI.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
}

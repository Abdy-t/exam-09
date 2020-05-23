package com.example.exam9.domain.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> getAllByTheme_Id(int id);

    Page<Comment> findAllByTheme_Id(int id, Pageable pageable);
}

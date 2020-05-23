package com.example.exam9.domain.comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDTO> getComments(Pageable pageable) {
        return commentService.getPageComments(pageable).getContent();
    }

    @GetMapping("/{id:\\d+?}")
    public CommentDTO getComment(@PathVariable int id) {
        return commentService.getComment(id);
    }
}

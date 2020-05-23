package com.example.exam9.domain.comment;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentDTO> getComments(int idTheme) {
        var list = commentRepository.getAllByTheme_Id(idTheme);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment c : list) {
            commentDTOs.add(CommentDTO.from(c));
        }
        return commentDTOs;
    }

    public Page<CommentDTO> getPageComments(int idTheme, Pageable pageable) {
        var list = commentRepository.findAllByTheme_Id(idTheme, pageable);
        return list.map(CommentDTO::from);
    }

    public Page<CommentDTO> getPageComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(CommentDTO::from);
    }

    public CommentDTO getComment(int id) {
        return CommentDTO.from(commentRepository.findById(id).get());
    }

//    public CommentDTO addComment(UserRegisterForm form) {
//        if (repository.existsByEmail(form.getEmail())) {
//            throw new UserAlreadyRegisteredException();
//        }
//
//        var user = User.builder()
//                .email(form.getEmail())
//                .fullname(form.getName())
//                .password(encoder.encode(form.getPassword()))
//                .build();
//
//        repository.save(user);
//
//        return UserResponseDTO.from(user);
//    }

}

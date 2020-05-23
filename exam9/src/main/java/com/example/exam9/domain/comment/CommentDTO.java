package com.example.exam9.domain.comment;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDTO {
    private Integer id;
    private String text;
    private String email;
    private int theme;
    private String date;

    public static CommentDTO from(Comment comment) {
        return builder()
                .id(comment.getId())
                .text(comment.getText())
                .email(comment.getUser().getEmail())
                .theme(0)
                .date(comment.getDate().toString())
                .build();
    }
}

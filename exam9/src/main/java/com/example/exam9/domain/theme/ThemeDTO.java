package com.example.exam9.domain.theme;

import com.example.exam9.domain.comment.CommentDTO;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThemeDTO {
    private Integer id;
    private String title;
    private String email;
    private Page<CommentDTO> comment;
    private String date;
    private int count;

    public static ThemeDTO from(Theme theme) {
        return builder()
                .id(theme.getId())
                .title(theme.getTitle())
                .email(theme.getUser().getFullname())
                .comment(null)
                .date(theme.getDate().toString())
                .count(theme.getCount())
                .build();
    }

}

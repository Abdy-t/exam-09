package com.example.exam9.domain.comment;

import com.example.exam9.domain.theme.Theme;
import com.example.exam9.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name="comments")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 140)
    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme", referencedColumnName = "id")
    private Theme theme;

    @Column
    private LocalDateTime date;
}

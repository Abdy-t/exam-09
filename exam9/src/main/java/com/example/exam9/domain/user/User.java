package com.example.exam9.domain.user;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Table(name="users")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotBlank
    @Size(min = 5, max = 128)
    @Column(length = 128)
    private String email;

    @NotBlank
    @Size(min = 3, max = 128)
    @Column(length = 128)
    private String password;

    @NotBlank
    @Size(min = 3, max = 128)
    @Column(length = 128)
    private String fullname;

    @Column
    @Builder.Default
    private boolean enabled = true;

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(length = 128)
    @Builder.Default
    private String role = "USER";

}

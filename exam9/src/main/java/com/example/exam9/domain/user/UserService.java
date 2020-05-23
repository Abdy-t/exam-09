package com.example.exam9.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
//    private final CartRepository cartRepository;

    private final PasswordEncoder encoder;

    public UserResponseDTO register(UserRegisterForm form) {
        if (repository.existsByEmail(form.getEmail())) {
            throw new UserAlreadyRegisteredException();
        }

        var user = User.builder()
                .email(form.getEmail())
                .fullname(form.getName())
                .password(encoder.encode(form.getPassword()))
                .build();

        repository.save(user);

        return UserResponseDTO.from(user);
    }

    public UserResponseDTO getByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return UserResponseDTO.from(user);
    }
}

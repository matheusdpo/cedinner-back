package br.com.matheusdpo.cedinner.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.matheusdpo.cedinner.application.dto.RegisterUserRequest;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.UserJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.repositories.UserJpaRepository;

@Service
public class RegisterUserUseCase {
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserJpaRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void execute(RegisterUserRequest request) {
        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Create new user
        UserJpaEntity user = new UserJpaEntity();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        userRepository.save(user);
    }
}


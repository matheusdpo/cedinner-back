package br.com.matheusdpo.cedinner.infrastructure.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.UserJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.repositories.UserJpaRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserJpaRepository userRepository;

    public UserDetailsServiceImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserJpaEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}


package br.com.matheusdpo.cedinner.domain.repositories;

import br.com.matheusdpo.cedinner.domain.entities.User;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId userId);
    Optional<User> findByUsername(String username);
    boolean existsById(UserId userId);
    void delete(User user);
}

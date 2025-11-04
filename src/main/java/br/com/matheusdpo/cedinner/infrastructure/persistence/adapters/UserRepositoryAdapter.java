package br.com.matheusdpo.cedinner.infrastructure.persistence.adapters;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.matheusdpo.cedinner.domain.entities.User;
import br.com.matheusdpo.cedinner.domain.repositories.UserRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.UserJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.repositories.UserJpaRepository;

@Component
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = toJpaEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(jpaEntity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId.getValue())
                .map(this::toDomainEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(this::toDomainEntity);
    }

    @Override
    public boolean existsById(UserId userId) {
        return userJpaRepository.existsById(userId.getValue());
    }

    @Override
    public void delete(User user) {
        UserJpaEntity jpaEntity = toJpaEntity(user);
        userJpaRepository.delete(jpaEntity);
    }

    private UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        if (user.getId() != null) {
            entity.setId(user.getId().getValue());
        }
        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
        entity.setAddress(user.getAddress());
        return entity;
    }

    private User toDomainEntity(UserJpaEntity entity) {
        User user = new User(
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress()
        );
        user.setId(new UserId(entity.getId()));
        return user;
    }
}

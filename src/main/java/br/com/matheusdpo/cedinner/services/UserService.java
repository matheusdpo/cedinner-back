package br.com.matheusdpo.cedinner.services;

import br.com.matheusdpo.cedinner.entities.User;
import br.com.matheusdpo.cedinner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario nao encontrado");
        }

        return userOptional.get();
    }

    public User findUserByUsername(String username) {


        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return null;
        }

        return userOptional.get();
    }

    public boolean isUserExistById(Long id) {
        try {
            User user = findUserById(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }
}

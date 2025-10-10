package br.com.matheusdpo.cedinner.controllers;

import br.com.matheusdpo.cedinner.entities.User;
import br.com.matheusdpo.cedinner.entities.UserVO;
import br.com.matheusdpo.cedinner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        try {
            String username = newUser.getUsername();

            if (username.isEmpty()) {
                throw new IllegalArgumentException("Usuario nao encontrado");
            }

            User userCreated = userService.findUserByUsername(username);

            if (Objects.nonNull(userCreated)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Usuario ja existente");
            }

            userService.save(newUser);

            return ResponseEntity.ok("Usuario criado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Usuario nao encontrado");
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserVO user) {
        try {
            String username = user.getUsername();

            if (username.isEmpty()) {
                throw new IllegalArgumentException("Usuario nao encontrado");
            }

            User userCreated = userService.findUserByUsername(username);
            if (Objects.isNull(userCreated)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Usuario ou senha incorretos");
            }

            return ResponseEntity.ok(userCreated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Usuario ou senha incorretos");
        }
    }
}

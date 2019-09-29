package pl.sda.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.task.UserRepository;
import pl.sda.task.model.User;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> getUser() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) throws URISyntaxException {
        User savedUser = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/users/" + savedUser.getId())).build();
    }
}

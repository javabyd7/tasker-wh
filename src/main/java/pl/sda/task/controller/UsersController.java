package pl.sda.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.task.model.User;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private User user;

    @GetMapping
    public User getUser() {
        return user;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) throws URISyntaxException {
        this.user = user;
        return ResponseEntity.created(new URI("/api/users/" + 1)).build();
    }

}

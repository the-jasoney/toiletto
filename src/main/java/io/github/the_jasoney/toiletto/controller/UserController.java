package io.github.the_jasoney.toiletto.controller;

import io.github.the_jasoney.toiletto.entity.Toilet;
import io.github.the_jasoney.toiletto.entity.User;
import io.github.the_jasoney.toiletto.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    //get user by id, get user by username

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id cannot be null");
        Optional<User> user = userDetailsService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }

    @GetMapping(path = "/get/{username}")
    public ResponseEntity<User> getUserbyUserName(@PathVariable("username") String username) {
        if (username == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username cannot be null");
        Optional<User> user = userDetailsService.getUserByUserName(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }



}

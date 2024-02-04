package io.github.the_jasoney.toiletto.controller;

import io.github.the_jasoney.toiletto.entity.User;
import io.github.the_jasoney.toiletto.service.UserDetailsService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    //get user by id, get user by username

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<User> getUserById(
        @PathVariable("id")
        @NotBlank
        String id
    ) {
        Optional<User> user = userDetailsService.getUserById(id);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping(path = "/get/{username}")
    public ResponseEntity<User> getUserbyUserName(
        @PathVariable("username")
        @NotBlank
        String username
    ) {
        Optional<User> user = userDetailsService.getUserByUserName(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



}

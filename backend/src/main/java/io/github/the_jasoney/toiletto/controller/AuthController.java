package io.github.the_jasoney.toiletto.controller;

import io.github.the_jasoney.toiletto.entity.ERole;
import io.github.the_jasoney.toiletto.entity.Role;
import io.github.the_jasoney.toiletto.entity.User;
import io.github.the_jasoney.toiletto.payloads.request.auth.LoginRequest;
import io.github.the_jasoney.toiletto.payloads.request.auth.SignupRequest;
import io.github.the_jasoney.toiletto.payloads.response.auth.JwtResponse;
import io.github.the_jasoney.toiletto.payloads.response.MessageResponse;
import io.github.the_jasoney.toiletto.repository.RoleRepository;
import io.github.the_jasoney.toiletto.repository.UserRepository;
import io.github.the_jasoney.toiletto.security.jwt.JwtUtils;
import io.github.the_jasoney.toiletto.service.UserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
            .stream().map(GrantedAuthority::getAuthority).toList();


        return ResponseEntity
            .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: email already in use"));
        }

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username already in use"));
        }

        User user = new User(
            signupRequest.getUsername(),
            passwordEncoder.encode(signupRequest.getPassword()),
            signupRequest.getEmail()
        );

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: role not found. Was it declared in data.sql?"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}

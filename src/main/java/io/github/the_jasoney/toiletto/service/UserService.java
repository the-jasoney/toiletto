package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.component.User;
import io.github.the_jasoney.toiletto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}

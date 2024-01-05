package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;
}

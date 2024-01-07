package io.github.the_jasoney.toiletto.controller;

import io.github.the_jasoney.toiletto.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "token")
public class TokenController {

    @Autowired
    private TokenService tokenService;
}

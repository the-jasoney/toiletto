package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.component.Token;
import io.github.the_jasoney.toiletto.component.User;
import io.github.the_jasoney.toiletto.repository.TokenRepository;
import io.github.the_jasoney.toiletto.util.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public boolean authenticateToken(String token) {
        //make secure later
        return tokenRepository.existsById(token);
    }

    public String newToken(User user) throws TokenException {
        Token token = new Token(user);
        return tokenRepository.save(token).getToken();
    }

    public void deleteToken(String token) throws TokenException {
        if (tokenRepository.existsById(token))
            tokenRepository.deleteById(token);
        else throw new TokenException("Invalid token");
    }

    public Optional<User> getUser(String token) {
        Optional<Token> t = tokenRepository.findById(token);
        return t.map(Token::getToilettouser);
    }
}

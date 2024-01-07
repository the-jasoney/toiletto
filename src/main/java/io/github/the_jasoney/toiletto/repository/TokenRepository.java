package io.github.the_jasoney.toiletto.repository;

import io.github.the_jasoney.toiletto.component.Toilet;
import io.github.the_jasoney.toiletto.component.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
}


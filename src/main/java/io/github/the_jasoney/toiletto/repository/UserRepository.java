package io.github.the_jasoney.toiletto.repository;

import io.github.the_jasoney.toiletto.component.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}

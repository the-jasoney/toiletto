package io.github.the_jasoney.toiletto.repository;

import io.github.the_jasoney.toiletto.component.ERole;
import io.github.the_jasoney.toiletto.component.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

package io.github.the_jasoney.toiletto.repository;

import io.github.the_jasoney.toiletto.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}

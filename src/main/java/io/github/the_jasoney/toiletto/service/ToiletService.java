package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.component.Review;
import io.github.the_jasoney.toiletto.component.Toilet;
import java.util.*;

import java.util.Optional;

public interface ToiletService {
    public Optional<Toilet> getToiletById(Integer id);

    public List<Review> getToiletReviews(Integer id);

}
package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.component.Review;
import io.github.the_jasoney.toiletto.component.Toilet;
import io.github.the_jasoney.toiletto.repository.ToiletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.*;

@Service
public class ToiletServiceImpl implements ToiletService {

    @Autowired
    private ToiletRepository toiletRepository;

    @Override
    public Optional<Toilet> getToiletById(Integer id) {
        return toiletRepository.findById(id);
    }

    @Override
    public List<Review> getToiletReviews(Integer id) {
        Optional<Toilet> toilet = toiletRepository.findById(id);
        if (toilet.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Toilet not found"
            );
        }
        return toilet.get().getReviews();
    }
}

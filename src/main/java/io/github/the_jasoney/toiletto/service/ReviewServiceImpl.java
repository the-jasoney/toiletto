package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl {
    
    @Autowired
    private ReviewRepository reviewRepository;
}

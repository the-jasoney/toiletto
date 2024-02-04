package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.entity.*;
import io.github.the_jasoney.toiletto.repository.ReviewRepository;
import io.github.the_jasoney.toiletto.repository.ToiletRepository;
import io.github.the_jasoney.toiletto.repository.UserRepository;
import io.github.the_jasoney.toiletto.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ToiletRepository toiletRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToiletService toiletService;

    public Optional<Review> getById(String id) {
        return reviewRepository.findById(id);
    }

    public String newReview(
        String toiletId,
        String userId,
        String content,
        Status status,
        BathroomType bathroomType,
        Optional<Integer> accessibilityRating,
        Optional<Integer> cleanlinessRating,
        Optional<Integer> privacyRating,
        Optional<Boolean> paymentRequired,
        Optional<Boolean> singleStall,
        Optional<Boolean> hasBabyChangingStation
    ) throws NotFoundException {
        Logger logger = LoggerFactory.getLogger(ReviewService.class);

        Review review = new Review();

        Optional<Toilet> toilet = toiletRepository.findById(toiletId);
        if (toilet.isPresent())
            review.setToilet(toilet.get());
        else throw new NotFoundException("user not found by id");

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            review.setAuthor(user.get());
        else throw new NotFoundException("toilet not found by id");

        review.setContent(content);
        review.setStatus(status);
        review.setBathroomType(bathroomType);

        accessibilityRating.ifPresent(review::setAccessibilityRating);
        cleanlinessRating.ifPresent(review::setCleanlinessRating);
        privacyRating.ifPresent(review::setPrivacyRating);
        paymentRequired.ifPresent(review::setPaymentRequired);
        singleStall.ifPresent(review::setSingleStall);
        hasBabyChangingStation.ifPresent(review::setHasBabyChangingStation);

        logger.trace(String.valueOf(review));

        String id = reviewRepository.save(review).getId();

        toiletService.updateScores(toiletId);

        return id;
    }
}

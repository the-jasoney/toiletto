package io.github.the_jasoney.toiletto.controller;

import io.github.the_jasoney.toiletto.entity.Review;
import io.github.the_jasoney.toiletto.payloads.request.review.CreateReviewRequest;
import io.github.the_jasoney.toiletto.service.ReviewService;
import io.github.the_jasoney.toiletto.service.UserDetails;
import io.github.the_jasoney.toiletto.util.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/reviews")
@CrossOrigin
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Review> getReview(
        @PathVariable
        @NotBlank
        String id
    ) {
        Optional<Review> review = reviewService.getById(id);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createReview(
        @AuthenticationPrincipal
        UserDetails userDetails,

        @Valid @RequestBody @NotNull
        CreateReviewRequest request
    ) {
        try {
            String reviewId = reviewService.newReview(
                request.getToiletId(),
                userDetails.getId(),
                request.getContent(),
                request.getStatus(),
                request.getBathroomType(),
                Optional.ofNullable(request.getAccessibilityRating()),
                Optional.ofNullable(request.getCleanlinessRating()),
                Optional.ofNullable(request.getPrivacyRating()),
                Optional.ofNullable(request.getPaymentRequired()),
                Optional.ofNullable(request.getSingleStall()),
                Optional.ofNullable(request.getBabyChangingStation())
            );
            return ResponseEntity.created(URI.create("reviews/get/" + reviewId)).body(reviewId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

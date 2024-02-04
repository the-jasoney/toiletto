package io.github.the_jasoney.toiletto.service;

import io.github.the_jasoney.toiletto.entity.Review;
import io.github.the_jasoney.toiletto.entity.Toilet;
import io.github.the_jasoney.toiletto.repository.ToiletRepository;
import io.github.the_jasoney.toiletto.util.AlreadyExistsException;
import io.github.the_jasoney.toiletto.util.NotFoundException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToiletService {

    @Autowired
    private ToiletRepository toiletRepository;

    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    public List<Toilet> getToiletsNearLocation(Float latitude, Float longitude, Float maxDist, int skip, int take) {
        Point p = factory.createPoint(new Coordinate(latitude, longitude));
        return toiletRepository.findNearWithinDistance(p, maxDist, skip, take);
    }

    public Boolean toiletExists(String id) {
        return toiletRepository.existsById(id);
    }

    public Optional<Toilet> getToiletById(String id) {
        return toiletRepository.findById(id);
    }

    public List<Review> getToiletReviews(String id) {
        Optional<Toilet> toilet = toiletRepository.findById(id);
        if (toilet.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Toilet not found"
            );
        }

        return toilet.get().getReviews();
    }

    public String createToilet(Double latitude, Double longitude) throws AlreadyExistsException {
        Point point = factory.createPoint(new Coordinate(latitude, longitude));

        if (toiletRepository.existsByLocation(point)) {
            throw new AlreadyExistsException("Toilet already exists with that location");
        }

        Toilet toilet = toiletRepository.save(new Toilet(point));
        return toilet.getId();
    }

    public void updateScores(
        String toiletId
    ) throws NotFoundException {
        Optional<Toilet> toiletOptional = toiletRepository.findById(toiletId);
        Toilet toilet = toiletOptional.orElseThrow(() -> new NotFoundException("Toilet not found"));
        List<Review> reviews = toilet.getReviews();

        List<Integer> accessibilityScores = new ArrayList<>();
        // To remove null values from list
        for (Optional<Integer> v : reviews.stream().map(v -> Optional.ofNullable(v.getAccessibilityRating())).toList())
            v.ifPresent(accessibilityScores::add);

        List<Integer> privacyScores = new ArrayList<>();
        for (Optional<Integer> v : reviews.stream().map(v -> Optional.ofNullable(v.getPrivacyRating())).toList())
            v.ifPresent(privacyScores::add);

        List<Integer> cleanlinessScores = new ArrayList<>();
        for (Optional<Integer> v : reviews.stream().map(v -> Optional.ofNullable(v.getCleanlinessRating())).toList())
            v.ifPresent(cleanlinessScores::add);

        Float accessibilityAverage = (float) accessibilityScores
            .stream()
            .mapToInt((a) -> a)
            .summaryStatistics()
            .getAverage();

        Float cleanlinessAverage = (float) cleanlinessScores
            .stream()
            .mapToInt((a) -> a)
            .summaryStatistics()
            .getAverage();

        Float privacyAverage = (float) privacyScores
            .stream()
            .mapToInt((a) -> a)
            .summaryStatistics()
            .getAverage();

        toilet.setAccessibilityScore(accessibilityAverage);
    }
}

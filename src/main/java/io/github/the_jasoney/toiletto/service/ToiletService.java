package io.github.the_jasoney.toiletto.service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import io.github.the_jasoney.toiletto.entity.Review;
import io.github.the_jasoney.toiletto.entity.Toilet;
import io.github.the_jasoney.toiletto.repository.ToiletRepository;
import io.github.the_jasoney.toiletto.util.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.*;

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

    public String createToiletFromAddress(String address) throws AlreadyExistsException {
        if (toiletRepository.existsByAddress(address)) {
            throw new AlreadyExistsException("Toilet already exists with that address");
        }
        Toilet toilet = toiletRepository.save(new Toilet(address));
        return toilet.getId();
    }

    public String createToiletFromCoords(Double latitude, Double longitude) throws AlreadyExistsException {
        Point point = factory.createPoint(new Coordinate(latitude, longitude));

        if (toiletRepository.existsByLocation(point)) {
            throw new AlreadyExistsException("Toilet already exists with that location");
        }

        Toilet toilet = toiletRepository.save(new Toilet(point));
        return toilet.getId();
    }

    public List<Review> getReviewsByToiletId(String id){
        Optional<Toilet> toilet = toiletRepository.findById(id);
        if(toilet.isPresent()) {
            return toilet.get().getReviews();
        }
        throw HttpClientErrorException.NotFound
    }
}

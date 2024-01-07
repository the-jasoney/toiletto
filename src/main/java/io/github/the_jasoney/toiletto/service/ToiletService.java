package io.github.the_jasoney.toiletto.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import io.github.the_jasoney.toiletto.component.Review;
import io.github.the_jasoney.toiletto.component.Toilet;
import io.github.the_jasoney.toiletto.repository.ToiletRepository;
import io.github.the_jasoney.toiletto.util.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.*;

@Service
public class ToiletService {

    @Autowired
    private ToiletRepository toiletRepository;

    @Autowired
    private TokenService tokenService;

    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    public List<Toilet> getToiletsNearLocation(Float latitude, Float longitude, Float maxDist) {
        Point p = factory.createPoint(new Coordinate(latitude, longitude));
        return toiletRepository.findNearWithinDistance(p, maxDist);
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

    public String createToiletFromAddress(String token, String address) throws UnauthenticatedException {
        // make more secure later
        if (tokenService.authenticateToken(token)) {
            Toilet toilet = toiletRepository.save(new Toilet(address));
            return toilet.getId();
        } else throw new UnauthenticatedException("invalid token");
    }

    public String createToiletFromCoords(String token, Float latitude, Float longitude) throws UnauthenticatedException {
        if (!tokenService.authenticateToken(token)) throw new UnauthenticatedException("Invalid token");
        Point p = factory.createPoint(new Coordinate(latitude, longitude));
        Toilet toilet = toiletRepository.save(new Toilet(p));
        return toilet.getId();
    }
}

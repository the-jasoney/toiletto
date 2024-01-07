package io.github.the_jasoney.toiletto.controller;


import io.github.the_jasoney.toiletto.component.Review;
import io.github.the_jasoney.toiletto.component.Toilet;
import io.github.the_jasoney.toiletto.service.ToiletService;
import io.github.the_jasoney.toiletto.util.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping(path = "/toilets", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ToiletController {

    @Autowired
    private ToiletService toiletService;

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Toilet> getToiletById(
            @PathVariable("id") String id
    ){
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id cannot be null");
        Optional<Toilet> toilet = toiletService.getToiletById(id);
        if (toilet.isPresent()) {
            return ResponseEntity.ok(toilet.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Toilet not found"
            );
        }
    }

    @GetMapping(path = "/getNear")
    public ResponseEntity<List<Toilet>> getToiletNearMe(
            Float latitude,
            Float longitude,
            Float maxDist
    ) {
        return ResponseEntity.ok(toiletService.getToiletsNearLocation(latitude, longitude, maxDist));
    }

    @GetMapping(path = "/getReviews")
    public ResponseEntity<List<Review>> getToiletReview(String id) {
        if (toiletService.toiletExists(id))
            return ResponseEntity.ok(toiletService.getToiletReviews(id));
        else return ResponseEntity.notFound().build();
    }

    @PostMapping(
            path = "/create/address",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createToiletByAddress(
            @RequestBody String token,
            @RequestBody String address
    ) {
        try {
            return ResponseEntity.ok().body(toiletService.createToiletFromAddress(token, address));
        } catch (UnauthenticatedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(
            path = "/createToilet/coords",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createToiletByCoordinates(
            @RequestBody String token,
            @RequestBody Float longitude,
            @RequestBody Float latitude
    ) {
        try {
            return ResponseEntity.ok().body(
                    toiletService.createToiletFromCoords(token, longitude, latitude)
            );
        } catch (UnauthenticatedException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid token"
            );
        }
    }
}

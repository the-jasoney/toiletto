package io.github.the_jasoney.toiletto.controller;


import io.github.the_jasoney.toiletto.entity.Review;
import io.github.the_jasoney.toiletto.entity.Toilet;
import io.github.the_jasoney.toiletto.payloads.request.toilet.CreateToiletByAddressRequest;
import io.github.the_jasoney.toiletto.payloads.request.toilet.CreateToiletByCoordinatesRequest;
import io.github.the_jasoney.toiletto.payloads.response.MessageResponse;
import io.github.the_jasoney.toiletto.payloads.response.toilet.ToiletCreatedResponse;
import io.github.the_jasoney.toiletto.service.ToiletService;
import io.github.the_jasoney.toiletto.util.AlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            Float maxDist,
            Integer skip,
            Integer take
    ) {
        return ResponseEntity.ok(toiletService.getToiletsNearLocation(latitude, longitude, maxDist, skip, (int) take));
    }

    @GetMapping(path = "/getReviews")
    public ResponseEntity<List<Review>> getToiletReview(String id) {
        if (toiletService.toiletExists(id))
            return ResponseEntity.ok(toiletService.getToiletReviews(id));
        else return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/create/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createToiletByAddress(
        @Valid
        @RequestBody
        CreateToiletByAddressRequest request
    ) {
        try {
            return ResponseEntity.ok(toiletService.createToiletFromAddress(request.getAddress()));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/create/coords")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createToiletByCoordinates(
        @Valid
        @RequestBody
        CreateToiletByCoordinatesRequest request
    ) {
        try {
            return ResponseEntity.ok().body(
                new ToiletCreatedResponse(
                    toiletService.createToiletFromCoords(request.getLatitude(), request.getLongitude())
                )
            );
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}

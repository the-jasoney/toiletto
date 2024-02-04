package io.github.the_jasoney.toiletto.controller;


import io.github.the_jasoney.toiletto.entity.Review;
import io.github.the_jasoney.toiletto.entity.Toilet;
import io.github.the_jasoney.toiletto.payloads.request.toilet.CreateToiletRequest;
import io.github.the_jasoney.toiletto.payloads.response.MessageResponse;
import io.github.the_jasoney.toiletto.service.ToiletService;
import io.github.the_jasoney.toiletto.util.AlreadyExistsException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/toilets", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ToiletController {

    @Autowired
    private ToiletService toiletService;

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Toilet> getToiletById(
            @PathVariable("id") @NotBlank
            String id
    ) {
        Optional<Toilet> toilet = toiletService.getToiletById(id);
        return toilet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/nearMe")
    public ResponseEntity<List<Toilet>> findToiletNearLocation(
            Float latitude,
            Float longitude,
            Float maxDist,
            Integer skip,
            Integer take
    ) {
        return ResponseEntity.ok(
            toiletService.getToiletsNearLocation(latitude, longitude, maxDist, skip, take)
        );
    }

    @GetMapping(path = "/reviews")
    public ResponseEntity<List<Review>> getToiletReviews(
        @RequestParam
        String id
    ) {
        Optional<Toilet> toilet = toiletService.getToiletById(id);
        return toilet.map(value -> ResponseEntity.ok(
            value.getReviews()
        )).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createToilet(
        @Valid
        @RequestBody
        CreateToiletRequest request
    ) {
        try {
            String toiletId = toiletService.createToilet(request.getLatitude(), request.getLongitude());
            return ResponseEntity.created(URI.create("toilets/get/" + toiletId)).body(toiletId);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}

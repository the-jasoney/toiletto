package io.github.the_jasoney.toiletto.controller;


import io.github.the_jasoney.toiletto.component.Review;
import io.github.the_jasoney.toiletto.component.Toilet;
import io.github.the_jasoney.toiletto.service.ToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping(path = "/toilets")
@CrossOrigin
public class ToiletController {

    @Autowired
    private ToiletService toiletService;
    //get toilet by id, toilet near me, get review from toilet
    @GetMapping(path = "/get")
    public Toilet getToiletById(Integer id){
        Optional<Toilet> toilet = toiletService.getToiletById(id);
        if (toilet.isPresent()) {
            return toilet.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Toilet not found"
            );
        }
    }

    @GetMapping(path = "/getToiletNearMe")
    public List<Toilet> getToiletNearMe(Float latitude, Float longitude, Float maxDistance) {
        return new ArrayList<Toilet>(); // TODO: implement
    }

    @GetMapping(path = "/getToiletReview")
    public List<Review> getToiletReview(Integer id) {
        return toiletService.getToiletReviews(id);
    }
}

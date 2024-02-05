package io.github.the_jasoney.toiletto.entity.serializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.the_jasoney.toiletto.entity.Review;

public class ReviewConverter extends StdConverter<Review, String> {
    @Override
    public String convert(Review review) {
        return review.getId();
    }
}

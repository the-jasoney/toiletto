package io.github.the_jasoney.toiletto.entity.serializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.the_jasoney.toiletto.entity.Toilet;

public class ToiletConverter extends StdConverter<Toilet, String> {
    @Override
    public String convert(Toilet toilet) {
        return toilet.getId();
    }
}

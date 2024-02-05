package io.github.the_jasoney.toiletto.entity.serializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.the_jasoney.toiletto.entity.User;

public class UserConverter extends StdConverter<User, String> {
    @Override
    public String convert(User user) {
        return user.getId();
    }
}

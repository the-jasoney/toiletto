package io.github.the_jasoney.toiletto.payload_safe;

import io.github.the_jasoney.toiletto.entity.Review;
import io.github.the_jasoney.toiletto.entity.User;

import java.util.List;

/// A User class that is safe to send to people who are not the user.
/// It does not contain any sensitive information such as email or password.
public record PayloadSafeUser(
    String id,
    String username,
    List<String> reviewIds
) {
    public static PayloadSafeUser fromEntity(User user) {
        return new PayloadSafeUser(
            user.getId(),
            user.getUsername(),
            user
                .getReviews()
                .stream().map(Review::getId)
                .toList()
        );
    }
}

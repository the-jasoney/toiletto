package io.github.the_jasoney.toiletto.component;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer token;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public Instant dateCreated = Instant.now();

    @Column
    public Instant dateExpires = Instant.now().plus(1, ChronoUnit.DAYS);
}

package io.github.the_jasoney.toiletto.component;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String token;

    @ManyToOne(targetEntity = User.class)
    private User toilettouser;

    @Column
    private Instant dateCreated = Instant.now();

    @Column
    private Instant dateExpires = Instant.now().plus(1, ChronoUnit.DAYS);

    public Token(User toilettouser) {
        this.toilettouser = toilettouser;
    }

    public Token() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getToilettouser() {
        return toilettouser;
    }

    public void setToilettouser(User toilettouser) {
        this.toilettouser = toilettouser;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(Instant dateExpires) {
        this.dateExpires = dateExpires;
    }

}

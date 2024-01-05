package io.github.the_jasoney.toiletto.component;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.List;

@Entity
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(targetEntity = Review.class)
    private List<Review> reviews;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;

    @Column
    private Float accessibilityScore;

    @Column
    private Float cleanlinessScore;

    @Column
    private Float privacyScore;

    public Toilet() {
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getAccessibilityScore() {
        return accessibilityScore;
    }

    public void setAccessibilityScore(Float accessibilityScore) {
        this.accessibilityScore = accessibilityScore;
    }

    public Float getCleanlinessScore() {
        return cleanlinessScore;
    }

    public void setCleanlinessScore(Float cleanlinessScore) {
        this.cleanlinessScore = cleanlinessScore;
    }

    public Float getPrivacyScore() {
        return privacyScore;
    }

    public void setPrivacyScore(Float privacyScore) {
        this.privacyScore = privacyScore;
    }
}

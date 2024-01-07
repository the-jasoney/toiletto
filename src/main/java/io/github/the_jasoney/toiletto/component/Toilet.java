package io.github.the_jasoney.toiletto.component;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "toilets")
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(targetEntity = Review.class, mappedBy = "toilet")
    private List<Review> reviews;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @Column
    private Float accessibilityScore;

    @Column
    private Float cleanlinessScore;

    @Column
    private Float privacyScore;

    public Toilet() {
    }

    public Toilet(String address) {
        this.address = address;
        this.reviews = new ArrayList<Review>();
        this.location = (new GeometryFactory(new PrecisionModel(), 4326)).createPoint(new Coordinate(0, 0)); // TODO use geocoding API to fetch coordinates from address
    }

    public Toilet(Point location) {
        this.address = "";
        this.reviews = new ArrayList<Review>();
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

package io.github.the_jasoney.toiletto.component;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(targetEntity = Toilet.class)
    @JoinColumn(name="toilet_id", nullable = false)
    private Toilet toilet;

    @Column(nullable = false)
    private Instant creationDate;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BathroomType bathroomType;

    @Column
    private Integer accessibilityRating;

    @Column
    private Integer cleanlinessRating;

    @Column
    private Integer privacyRating;

    @Column
    private Boolean paymentRequired;

    @Column
    private Boolean singleStall;

    @Column
    private Boolean hasBabyChangingStation;

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BathroomType getBathroomType() {
        return bathroomType;
    }

    public void setBathroomType(BathroomType bathroomType) {
        this.bathroomType = bathroomType;
    }

    public Integer getAccessibilityRating() {
        return accessibilityRating;
    }

    public void setAccessibilityRating(Integer accessibilityRating) {
        this.accessibilityRating = accessibilityRating;
    }

    public Integer getCleanlinessRating() {
        return cleanlinessRating;
    }

    public void setCleanlinessRating(Integer cleanlinessRating) {
        this.cleanlinessRating = cleanlinessRating;
    }

    public Integer getPrivacyRating() {
        return privacyRating;
    }

    public void setPrivacyRating(Integer privacyRating) {
        this.privacyRating = privacyRating;
    }

    public Boolean getPaymentRequired() {
        return paymentRequired;
    }

    public void setPaymentRequired(Boolean paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public Boolean getSingleStall() {
        return singleStall;
    }

    public void setSingleStall(Boolean singleStall) {
        this.singleStall = singleStall;
    }

    public Boolean getHasBabyChangingStation() {
        return hasBabyChangingStation;
    }

    public void setHasBabyChangingStation(Boolean hasBabyChangingStation) {
        this.hasBabyChangingStation = hasBabyChangingStation;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Toilet getToilet() {
        return toilet;
    }

    public void setToilet(Toilet toilet) {
        this.toilet = toilet;
    }
}

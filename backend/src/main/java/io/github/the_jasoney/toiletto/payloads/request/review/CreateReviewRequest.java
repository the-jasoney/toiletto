package io.github.the_jasoney.toiletto.payloads.request.review;

import io.github.the_jasoney.toiletto.entity.BathroomType;
import io.github.the_jasoney.toiletto.entity.Status;
import jakarta.validation.constraints.NotBlank;

public class CreateReviewRequest {
    @NotBlank
    private String toiletId;

    private String content;

    private Status status;

    private BathroomType bathroomType;

    private Integer accessibilityRating;

    private Integer cleanlinessRating;

    private Integer privacyRating;

    private Boolean paymentRequired;

    private Boolean singleStall;

    private Boolean babyChangingStation;

    public CreateReviewRequest() {
    }

    public CreateReviewRequest(String toiletId, String userId, String content, Status status, BathroomType bathroomType, Integer accessibilityRating, Integer cleanlinessRating, Integer privacyRating, Boolean paymentRequired, Boolean singleStall, Boolean babyChangingStation) {
        this.toiletId = toiletId;
        this.content = content;
        this.status = status;
        this.bathroomType = bathroomType;
        this.accessibilityRating = accessibilityRating;
        this.cleanlinessRating = cleanlinessRating;
        this.privacyRating = privacyRating;
        this.paymentRequired = paymentRequired;
        this.singleStall = singleStall;
        this.babyChangingStation = babyChangingStation;
    }

    public String getToiletId() {
        return toiletId;
    }

    public void setToiletId(String toiletId) {
        this.toiletId = toiletId;
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

    public Boolean getBabyChangingStation() {
        return babyChangingStation;
    }

    public void setBabyChangingStation(Boolean babyChangingStation) {
        this.babyChangingStation = babyChangingStation;
    }
}

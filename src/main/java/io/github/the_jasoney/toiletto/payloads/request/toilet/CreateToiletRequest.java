package io.github.the_jasoney.toiletto.payloads.request.toilet;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateToiletRequest {
    @NotNull
    @Min(-90) @Max(90)
    private Double latitude;

    @NotNull
    @Min(-180) @Max(180)
    private Double longitude;

    public CreateToiletRequest(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

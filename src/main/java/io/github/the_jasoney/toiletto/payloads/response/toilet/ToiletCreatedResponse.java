package io.github.the_jasoney.toiletto.payloads.response.toilet;

import jakarta.validation.constraints.NotBlank;

public class ToiletCreatedResponse {
    @NotBlank
    private String id;

    public ToiletCreatedResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

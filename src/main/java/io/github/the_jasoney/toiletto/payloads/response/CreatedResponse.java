package io.github.the_jasoney.toiletto.payloads.response;

import jakarta.validation.constraints.NotBlank;

public class CreatedResponse {
    @NotBlank
    private String id;

    public CreatedResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

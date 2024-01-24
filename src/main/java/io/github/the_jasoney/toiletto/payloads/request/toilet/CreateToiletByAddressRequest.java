package io.github.the_jasoney.toiletto.payloads.request.toilet;

import jakarta.validation.constraints.NotBlank;

public class CreateToiletByAddressRequest {
    @NotBlank
    private String address;

    public CreateToiletByAddressRequest(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

package io.github.the_jasoney.toiletto.payloads.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

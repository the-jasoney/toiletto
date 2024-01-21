package io.github.the_jasoney.toiletto.payloads.request;


import jakarta.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(max = 25, message = "Username must not be longer than 25 characters")
    String username;

    @NotBlank
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String password;

    @NotBlank
    @Email
    String email;

    public SignupRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

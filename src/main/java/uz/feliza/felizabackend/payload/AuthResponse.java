package uz.feliza.felizabackend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String email;
    private String accessToken;

    public AuthResponse() {
    }

    public AuthResponse(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

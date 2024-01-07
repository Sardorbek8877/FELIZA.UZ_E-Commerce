package uz.feliza.felizabackend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
//    private String email;
    private String phoneNumber;
    private String accessToken;

    public AuthResponse() {
    }

    public AuthResponse(String phoneNumber, String accessToken) {
        this.phoneNumber = phoneNumber;
        this.accessToken = accessToken;
    }

    public void setPhoneNumber(String email) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

package uz.feliza.felizabackend.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequest{

    @Email
    @Length(min = 5, max = 50)
    String email;

    @Length(min = 5, max = 15)
    String password;
}

package uz.feliza.felizabackend.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class RegisterRequest{
        private String fullName;

        private String phoneNumber;
//        String email;

        private String password;

        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
        private Date birthDate;

        private String verifyCode;
}

package uz.feliza.felizabackend.payload;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserDto {

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String phoneNumber;

    private String password;

    private String email;

    private boolean enabled = false;

    private Set<Long> roles;
}

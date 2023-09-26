package uz.feliza.felizabackend.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {

    private Long customerId;

    @NotNull
    private String region;

    private String street;

    private String houseNumber;

    private String BTSAddress;
}

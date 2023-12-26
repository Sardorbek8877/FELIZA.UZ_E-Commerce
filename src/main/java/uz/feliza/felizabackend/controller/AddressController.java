package uz.feliza.felizabackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Address;
import uz.feliza.felizabackend.payload.AddressDto;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Address>> getAddressByCustomerId(@PathVariable Long id) {
        List<Address> addressesByUserId = addressService.getAddressesByUserId(id);
        return ResponseEntity.ok(addressesByUserId);
    }

    @PostMapping("/addAddress")
    public ResponseEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        HttpStatus httpStatus = apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }
}

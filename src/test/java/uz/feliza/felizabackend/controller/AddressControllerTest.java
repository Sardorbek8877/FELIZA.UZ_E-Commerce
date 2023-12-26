package uz.feliza.felizabackend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.feliza.felizabackend.entity.Address;
import uz.feliza.felizabackend.payload.AddressDto;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.service.AddressService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @Test
    void getAddresses() {

        List<Address> mockAddresses = new ArrayList<>();
        when(addressService.getAllAddresses()).thenReturn(mockAddresses);

        ResponseEntity<?> response = addressController.getAddresses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAddresses, response.getBody());
        verify(addressService, times(1)).getAllAddresses();
    }

    @Test
    void getAddressByCustomerId() {

        Long customerId = 1L;
        List<Address> mockAddresses = new ArrayList<>();
        when(addressService.getAddressesByUserId(customerId)).thenReturn(mockAddresses);

        ResponseEntity<?> response = addressController.getAddressByCustomerId(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAddresses, response.getBody());
        verify(addressService, times(1)).getAddressesByUserId(customerId);
    }

    @Test
    void addAddress() {
        AddressDto mockAddressDto = new AddressDto();
        ApiResponse mockApiResponse = new ApiResponse("Address added successfully", true);
        when(addressService.addAddress(mockAddressDto)).thenReturn(mockApiResponse);

        ResponseEntity<?> response = addressController.addAddress(mockAddressDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockApiResponse, response.getBody());
        verify(addressService, times(1)).addAddress(mockAddressDto);
    }
}
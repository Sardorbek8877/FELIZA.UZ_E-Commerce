package uz.feliza.felizabackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.feliza.felizabackend.entity.Address;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.payload.AddressDto;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.AddressRepository;
import uz.feliza.felizabackend.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private AddressService addressService;

//    @BeforeEach
//    public void setUp(){
//        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
//        addressService = new AddressService(addressRepository);
//    }

    @Test
    void getAllAddresses() {
        List<Address> mockAddresses = new ArrayList<>();
        when(addressRepository.findAll()).thenReturn(mockAddresses);

        List<Address> addresses = addressService.getAllAddresses();

        assertEquals(mockAddresses, addresses);
        verify(addressRepository).findAll();
    }

    @Test
    void getAddressesByUserId() {
        Long customerId = 1L;

        List<Address> mockAddresses = new ArrayList<>();
        when(addressRepository.findByCustomerId(customerId)).thenReturn(mockAddresses);

        List<Address> addresses = addressService.getAddressesByUserId(customerId);

        assertEquals(mockAddresses, addresses);
        verify(addressRepository).findByCustomerId(customerId);
    }

    @Test
    void addAddress() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCustomerId(1L);

        Optional<Customer> optionalCustomer = Optional.of(new Customer());
        when(customerRepository.findById(addressDto.getCustomerId())).thenReturn(optionalCustomer);

        ApiResponse response = addressService.addAddress(addressDto);

        assertTrue(response.isSuccess());
        assertEquals("Manzil qo'shildi", response.getMessage());

        verify(addressRepository).save(any(Address.class));
    }

    @Test
    public void testAddAddressCustomerNotFound() {
        AddressDto addressDto = new AddressDto();
        addressDto.setCustomerId(1L);
        // Mocking the behavior of customerRepository.findById() to return an empty optional
        when(customerRepository.findById(addressDto.getCustomerId())).thenReturn(Optional.empty());

        ApiResponse response = addressService.addAddress(addressDto);

        assertFalse(response.isSuccess());
        assertEquals("Haridor topilmadi", response.getMessage());

        verify(addressRepository, never()).save(any(Address.class));
    }
}
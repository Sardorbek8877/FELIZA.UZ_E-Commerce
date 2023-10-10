package uz.feliza.felizabackend.service;

import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.request.RegisterRequest;

import java.util.Optional;

public interface IAuthService {
    Customer register(RegisterRequest request);
    Optional<Customer> findByEmail(String email);
    void saveCustomerVerificationToken(Customer theUser, String verificationToken);
    String validateToken(String theToken);
}

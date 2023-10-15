package uz.feliza.felizabackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
    public ApiResponse updateCustomer(Customer customer, Integer id) {
        //todo: update customer
        return null;
    }
}

package uz.feliza.felizabackend.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping
    @RolesAllowed({"CUSTOMER"})
    public ResponseEntity<?> getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        if (customers.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(customers);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @Valid Customer customer){
        ApiResponse editedCustomer = customerService.updateCustomer(customer, id);
        return ResponseEntity.status(editedCustomer != null ? 202 : 409).body(editedCustomer);
    }
}

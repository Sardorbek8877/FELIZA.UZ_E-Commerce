package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

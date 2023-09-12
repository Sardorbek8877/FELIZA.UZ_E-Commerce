package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Category;
import uz.feliza.felizabackend.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByReferenceNumber(String referenceNumber);
}

package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByReferenceNumber(String referenceNumber);

    Optional<Product> findByReferenceNumberAndColorId(String refNumber, Long colorId);
}

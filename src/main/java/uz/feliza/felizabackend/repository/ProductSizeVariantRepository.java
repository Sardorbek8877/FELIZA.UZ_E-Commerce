package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Product;
import uz.feliza.felizabackend.entity.ProductSizeVariant;

import java.util.List;

public interface ProductSizeVariantRepository extends JpaRepository<ProductSizeVariant, Long> {

    boolean existsByBarCode(String barCode);


    List<ProductSizeVariant> findAllByProductId(Long id);
}

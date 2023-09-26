package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.CartItem;
import uz.feliza.felizabackend.entity.ProductSizeVariant;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    boolean existsByProductSizeVariantId(Long id);

    Optional<CartItem> findByProductSizeVariant(ProductSizeVariant productSizeVariant);

    List<CartItem> findAllByCustomerId(Long customerId);
}

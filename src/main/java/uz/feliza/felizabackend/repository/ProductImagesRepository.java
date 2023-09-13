package uz.feliza.felizabackend.repository;

import org.hibernate.validator.constraints.EAN;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.ProductImages;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {

    List<ProductImages> findAllByProductId(Long id);
}

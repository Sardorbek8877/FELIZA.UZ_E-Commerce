package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Category;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);
}

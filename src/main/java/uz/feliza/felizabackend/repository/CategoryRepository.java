package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}

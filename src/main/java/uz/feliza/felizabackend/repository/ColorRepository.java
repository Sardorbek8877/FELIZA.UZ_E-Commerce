package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Brand;
import uz.feliza.felizabackend.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {

    boolean existsByNameUZB( String name);


}

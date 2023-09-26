package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.LikedItem;

import java.util.List;

public interface LikedItemRepository extends JpaRepository<LikedItem, Long> {

    List<LikedItem> findAllByCustomerId(Long customerId);
}

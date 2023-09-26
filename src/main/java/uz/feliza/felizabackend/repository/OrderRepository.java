package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

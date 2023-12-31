package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Order;
import uz.feliza.felizabackend.entity.enums.OrderStatusType;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderStatusType(OrderStatusType orderStatusType);

    List<Order> findAllByCustomerId(Long customerId);

    Optional<Order> findByPostSendNumber(String postSendNumber);
}

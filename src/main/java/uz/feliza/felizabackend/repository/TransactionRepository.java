package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.feliza.felizabackend.entity.OrderTransaction;
import uz.feliza.felizabackend.entity.enums.TransactionState;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<OrderTransaction,Long> {
    Optional<OrderTransaction> findByPaymentId(String id);

    @Query("SELECT " +
                   "o " +
            "FROM OrderTransaction o " +
            "WHERE o.paymentTime BETWEEN ?1 AND ?2 AND o.state = ?3 " +
            "ORDER BY o.paymentTime ASC")
    List<OrderTransaction> findByPaymentTimeAndState(Date from, Date to, TransactionState state);
}

package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.CouponDetail;

import java.util.List;

public interface CouponDetailRepository extends JpaRepository<CouponDetail, Long> {

    List<CouponDetail> findAllByCustomerId(Long customerId);
}

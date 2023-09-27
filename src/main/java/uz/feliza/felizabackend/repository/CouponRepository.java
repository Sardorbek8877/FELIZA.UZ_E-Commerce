package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.feliza.felizabackend.entity.Coupon;
import uz.feliza.felizabackend.entity.Status;
import uz.feliza.felizabackend.projection.CustomCoupon;
import uz.feliza.felizabackend.projection.CustomStatus;

@RepositoryRestResource(path = "coupon", collectionResourceRel = "CouponList", excerptProjection = CustomCoupon.class)
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}

package uz.feliza.felizabackend.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.feliza.felizabackend.entity.Coupon;

@Projection(types = Coupon.class)
public interface CustomCoupon {

    Long getId();

    String getName();

    Long getCredit();
}

package uz.feliza.felizabackend.payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponDetailDto {

    private Long customerId;

    private LocalDate startDate;

    private LocalDate expireDate;

    private boolean isActive;

    private Long couponId;
}

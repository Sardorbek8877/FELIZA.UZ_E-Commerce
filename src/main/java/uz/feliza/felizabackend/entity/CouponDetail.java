package uz.feliza.felizabackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CouponDetail extends AbstractLongEntity {

    @ManyToOne
    private Customer customer;

    private LocalDate startDate;

    private LocalDate expireDate;

    private boolean isActive; // true false

    @ManyToOne
    private Coupon coupon;

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (expireDate.isBefore(startDate)) {
            throw new IllegalStateException("Tugash vaqti boshlanish vaqtidan keyin bo'lishi zarur.");
        }
    }
}

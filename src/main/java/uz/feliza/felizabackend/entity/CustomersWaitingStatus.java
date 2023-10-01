package uz.feliza.felizabackend.entity;

import jakarta.persistence.Entity;
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
public class CustomersWaitingStatus extends AbstractLongEntity {

    private Long customerId;

    private Long couponId;

    private LocalDate givenDate;
}

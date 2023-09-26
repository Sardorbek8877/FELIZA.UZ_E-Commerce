package uz.feliza.felizabackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order extends AbstractLongEntity {

    @ManyToOne
    private Customer customer;

    private Date orderTime;

    private String paymentMethod;

    private Long productCost;

    private String shippingStatus;

    private int deliveryDays;

    private Date deliveryDate;

    @ManyToOne
    private Address address;
}

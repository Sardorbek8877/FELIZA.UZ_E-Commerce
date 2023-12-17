package uz.feliza.felizabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.enums.OrderStatusType;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends AbstractLongEntity {

    @ManyToOne
    private Customer customer;

    @OneToMany
    private List<CartItem> cartItemList;

    private String orderNumber;

    private String postSendNumber;

    private LocalDate orderTime;

    private String paymentMethod;

    private Double orderCost;

    @Enumerated(value = EnumType.STRING)
    private OrderStatusType orderStatusType; //buyurtma berildi, jo'natildi, yetib bordi, bekor qilindi editOrderStatus()

    private String shippingStatus;

    //private boolean cancelled = false;

    private int deliveryDays;

    private LocalDate deliveryDate;

    @ManyToOne
    private Address address;

    private Boolean delivered;
}

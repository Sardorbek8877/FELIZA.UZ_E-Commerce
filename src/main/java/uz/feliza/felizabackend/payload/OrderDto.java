package uz.feliza.felizabackend.payload;

import lombok.Data;
import uz.feliza.felizabackend.entity.CartItem;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private Long customerId;

    private List<Long> cartItemIdList;

    private String paymentMethod;

    private Long orderCost;

//    private String orderStatus;

    private String shippingStatus;

    private int deliveryDays;

//    private LocalDate deliveryDate;

    private Long addressId;
}

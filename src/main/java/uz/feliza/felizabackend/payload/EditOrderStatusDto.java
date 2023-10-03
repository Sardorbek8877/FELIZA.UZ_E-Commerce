package uz.feliza.felizabackend.payload;

import lombok.Data;
import uz.feliza.felizabackend.entity.enums.OrderStatusType;

@Data
public class EditOrderStatusDto {

    private OrderStatusType orderStatusType;

    private String postSendNumber;
}

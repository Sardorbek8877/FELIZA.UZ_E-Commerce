package uz.feliza.felizabackend.payload;

import lombok.Data;

@Data
public class CartItemDto {

    private Long customerId;

    private Long productSizeVariantId;

    private int quantity;
}

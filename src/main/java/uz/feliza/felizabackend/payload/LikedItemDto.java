package uz.feliza.felizabackend.payload;

import lombok.Data;

@Data
public class LikedItemDto {

    private Long customerId;

    private Long productId;
}

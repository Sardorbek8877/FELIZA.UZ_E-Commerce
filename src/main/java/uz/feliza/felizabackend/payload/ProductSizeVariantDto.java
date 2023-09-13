package uz.feliza.felizabackend.payload;

import lombok.Data;

@Data
public class ProductSizeVariantDto {

    private String barCode;

    private int quantity;

    private String size;
}

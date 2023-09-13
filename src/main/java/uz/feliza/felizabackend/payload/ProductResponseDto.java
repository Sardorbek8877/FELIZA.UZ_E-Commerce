package uz.feliza.felizabackend.payload;

import lombok.Data;
import uz.feliza.felizabackend.entity.Product;
import uz.feliza.felizabackend.entity.ProductImages;
import uz.feliza.felizabackend.entity.ProductSizeVariant;

import java.util.List;

@Data
public class ProductResponseDto {

    private Product product;

    private List<Long> compatibleProducts;

    private List<ProductSizeVariant> productSizeVariantList;

    private List<ProductImages> productImagesList;
}

package uz.feliza.felizabackend.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductDto {

    @NotNull
    private String nameUZB;

    @NotNull
    private String nameRUS;

    @NotNull
    private String descriptionUZB;

    @NotNull
    private String descriptionRUS;

    @NotNull
    private String referenceNumber;

    @NotNull
    private Long price;

    @Max(value = 99, message = "Chegirma miqdori 100 dan ko'p bo'lmasligi kerak!")
    private Integer sale = 0;

    private Long brandId;

    @NotNull
    private List<Long> categoryId;

    private Long colorId;

    private List<Long> compatibleProductIdList;
    //private List<ProductIdDto> compatibleProductIdList;

    private List<ProductSizeVariantDto> productSizeVariantDtoList;
}

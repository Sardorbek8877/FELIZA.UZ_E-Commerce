package uz.feliza.felizabackend.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Long importPrice;

    @NotNull
    private Long sellPrice;

    @Max(value = 99, message = "Chegirma miqdori 100 dan ko'p bo'lmasligi kerak!")
    private Integer sale = 0;

    private Long brandId;

    @NotNull
    private List<Long> categoryId;

    @NotNull
    private Long colorId;

    private List<Long> compatibleProductsId;

    private List<ProductSizeVariantDto> productSizeVariantDtoList;

    public ProductDto(String nameUZB, String nameRUS, String descriptionUZB, String descriptionRUS,
                      String referenceNumber, Long importPrice, Long sellPrice, Integer sale, Long brandId,
                      List<Long> categoryId, Long colorId, List<ProductSizeVariantDto> productSizeVariantDtoList) {
        this.nameUZB = nameUZB;
        this.nameRUS = nameRUS;
        this.descriptionUZB = descriptionUZB;
        this.descriptionRUS = descriptionRUS;
        this.referenceNumber = referenceNumber;
        this.importPrice = importPrice;
        this.sellPrice = sellPrice;
        this.sale = sale;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.colorId = colorId;
        this.productSizeVariantDtoList = productSizeVariantDtoList;
    }
}

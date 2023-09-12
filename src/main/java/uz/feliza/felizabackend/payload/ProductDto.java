package uz.feliza.felizabackend.payload;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.feliza.felizabackend.entity.*;

import java.util.List;

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
    private Double price;

    @Max(value = 99, message = "Chegirma miqdori 100 dan ko'p bo'lmasligi kerak!")
    private Integer sale = 0;

    private Long brandId;

    @NotNull
    private List<Long> categoryId;

    private List<Long> compatibleProductIds;

    @NotNull
    private List<Long> imageIdList;
}

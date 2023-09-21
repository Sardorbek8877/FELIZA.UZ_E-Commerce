package uz.feliza.felizabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class Product extends AbstractLongEntity {

    @Column(nullable = false)
    private String nameUZB;

    @Column(nullable = false)
    private String nameRUS;

    private String descriptionUZB;

    private String descriptionRUS;

    @Column(nullable = false, unique = true)
    private String referenceNumber;

    @Column(nullable = false)
    private Long price;

    @Max(value = 99, message = "Chegirma miqdori 100% dan ko'p bo'lmasligi kerak!")
    private Integer sale = 0;

    @ManyToOne
    private Brand brand;

    @ManyToMany
    private List<Category> category;

    @ManyToOne
    private Color color;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> compatibleProducts;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImages> productImages;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductSizeVariant> productSizeVariantList;

    public Product(String nameUZB, String nameRUS, String descriptionUZB, String descriptionRUS,
                   String referenceNumber, Long price, Integer sale, Brand brand, List<Category> category,
                   Color color) {
        this.nameUZB = nameUZB;
        this.nameRUS = nameRUS;
        this.descriptionUZB = descriptionUZB;
        this.descriptionRUS = descriptionRUS;
        this.referenceNumber = referenceNumber;
        this.price = price;
        this.sale = sale;
        this.brand = brand;
        this.category = category;
        this.color = color;
    }

    public Product(String nameUZB, String nameRUS, String descriptionUZB, String descriptionRUS,
                   String referenceNumber, Long price, Integer sale, Brand brand, List<Category> category,
                   Color color, List<Product> compatibleProducts) {
        this.nameUZB = nameUZB;
        this.nameRUS = nameRUS;
        this.descriptionUZB = descriptionUZB;
        this.descriptionRUS = descriptionRUS;
        this.referenceNumber = referenceNumber;
        this.price = price;
        this.sale = sale;
        this.brand = brand;
        this.category = category;
        this.color = color;
        this.compatibleProducts = compatibleProducts;
    }
}

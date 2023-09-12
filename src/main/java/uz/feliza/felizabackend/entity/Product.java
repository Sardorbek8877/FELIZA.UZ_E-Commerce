package uz.feliza.felizabackend.entity;

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
@Entity
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductSizeVariant> productSizeVariantList;

    @ManyToMany
    private List<Category> category;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> compatibleProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImages> images;
}

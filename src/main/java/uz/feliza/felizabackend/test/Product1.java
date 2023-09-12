//package uz.feliza.felizabackend.test;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import uz.feliza.felizabackend.entity.Brand;
//import uz.feliza.felizabackend.entity.Category;
//import uz.feliza.felizabackend.entity.ProductVariant;
//import uz.feliza.felizabackend.entity.template.AbstractLongEntity;
//
//import java.util.List;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Product1 extends AbstractLongEntity {
//
//    @Column(nullable = false)
//    private String nameUZB;
//
//    @Column(nullable = false)
//    private String nameRUS;
//
//    private String descriptionUZB;
//
//    private String descriptionRUS;
//
//    @Column(nullable = false)
//    private Long price;
//
//    @Column(nullable = false, unique = true)
//    private String referenceNumber;// 10001
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<ProductVariant> variants;
//
//    @ManyToMany
//    private List<Category> category;
//
//    @ManyToOne
//    private Brand brand;
//}

//package uz.feliza.felizabackend.test;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Max;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import uz.feliza.felizabackend.entity.Brand;
//import uz.feliza.felizabackend.entity.Category;
//import uz.feliza.felizabackend.entity.Color;
//import uz.feliza.felizabackend.entity.template.AbstractLongEntity;
//
//import java.util.List;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class ProductHeader extends AbstractLongEntity {
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
//    private String referenceNumber;
//
//    @Column(nullable = false)
//    private Long price;
//
//    @Max(value = 99, message = "bbbbbbb")
//    private int sale = 0;
//
//    @ManyToOne
//    private Color color;
//
//    @ManyToMany
//    private List<Category> category;
//
//    @ManyToOne
//    private Brand brand;
//}

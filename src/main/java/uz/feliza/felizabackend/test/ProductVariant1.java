//package uz.feliza.felizabackend.test;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Max;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import uz.feliza.felizabackend.entity.Color;
//import uz.feliza.felizabackend.entity.Product;
//import uz.feliza.felizabackend.entity.Size;
//import uz.feliza.felizabackend.entity.template.AbstractLongEntity;
//
//import java.util.List;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class ProductVariant1 extends AbstractLongEntity {
//
//    @ManyToOne
//    private uz.feliza.felizabackend.entity.Product product;
//
//    @Column(unique = true,nullable = false)
//    private String barCode;
//
//    @Column(nullable = false)
//    private int quantity; //10, 12, 15
//
//    @Max(value = 99, message = "Chegirma miqdori 100% dan ko'p bo'lmasligi kerak!")
//    private Integer sale = 0;
//
//    @ManyToOne
//    private Color color;
//
//    @ManyToOne
//    private Size size;//s
//
//    //    @JoinTable(name = "product_compatibility",
////    joinColumns = { @JoinColumn(name = "product_id")},
////    inverseJoinColumns = { @JoinColumn(name = "compatible_product_id")})
//    @ManyToMany(cascade = CascadeType.ALL)
//    private List<Product> compatibleProducts;
//}

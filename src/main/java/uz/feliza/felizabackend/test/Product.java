//package uz.feliza.felizabackend.test;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import uz.feliza.felizabackend.entity.template.AbstractLongEntity;
//
//import java.util.List;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Product extends AbstractLongEntity {
//
//    @OneToOne
//    private ProductHeader productHeader;  // qora, id 101, ref 1001
//
//    @OneToMany
//    private List<Size> sizeList; // {sizeS, sizeM, sizeL}
//
//    @OneToMany
//    private List<Attachment> attachment;
//}

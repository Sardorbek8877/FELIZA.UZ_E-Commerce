package uz.feliza.felizabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductSizeVariant extends AbstractLongEntity {

    @Column(unique = true,nullable = false)
    private String barCode; //1234567

    @Column(nullable = false)
    private int quantity; // 12

    private String size; // M

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
}

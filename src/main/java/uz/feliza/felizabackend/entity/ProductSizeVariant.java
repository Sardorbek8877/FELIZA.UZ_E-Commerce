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
@Entity
public class ProductSizeVariant extends AbstractLongEntity {

    @Column(unique = true,nullable = false)
    private String barCode;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String size;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
}

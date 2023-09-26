package uz.feliza.felizabackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class OrderDetails extends AbstractLongEntity {

    @ManyToOne
    private Order order;

    @ManyToOne
    private ProductSizeVariant productSizeVariant;

    private int quantity; // 2

    private Long productCost;
}

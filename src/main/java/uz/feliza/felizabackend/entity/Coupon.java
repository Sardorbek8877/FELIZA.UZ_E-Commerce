package uz.feliza.felizabackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Coupon extends AbstractLongEntity {

    @Column(nullable = false)
    private String name; //8MART

    @Column(nullable = false)
    private Long credit; // 20 000
}

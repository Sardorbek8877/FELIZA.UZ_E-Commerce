package uz.feliza.felizabackend.entity;

import jakarta.persistence.Column;
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
public class Address extends AbstractLongEntity {

    @ManyToOne
    private Customer customer;

    @Column(nullable = false)
    private String region;

    private String street;

    private String houseNumber;

    private String BTSAddress; // filial
}

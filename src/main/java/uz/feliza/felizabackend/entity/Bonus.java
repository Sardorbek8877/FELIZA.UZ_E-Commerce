package uz.feliza.felizabackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bonus extends AbstractLongEntity {

    @ManyToOne
    private Customer customer;

    private Date givenDay;

    private boolean enabled; // true false

    @ManyToOne
    private BonusDetail bonusDetail;


    //addBonus(BonusDto b){
    //    .
    // }

    //tug'ilgan kunda avto  20 000 30 000 10 000
    //oxirgi tashrifda avtomat 20 kun, 30 kun
    //status oshganda avtomat
}

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
public class BonusDetail extends AbstractLongEntity {

    private String name;  //8MART    NAVRUZ   GOLDDAY  SILVERDAY BIRTHDAY

    private Long bonusCredit; // 20 000 so'm

    private int expireDay; // 10 kun
}

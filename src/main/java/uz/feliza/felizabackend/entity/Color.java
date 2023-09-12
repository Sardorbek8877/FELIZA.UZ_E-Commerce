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
public class Color extends AbstractLongEntity {

    @Column(nullable = false)
    private String nameUZB;

    @Column(nullable = false)
    private String nameRUS;

    private String colorCode;
}

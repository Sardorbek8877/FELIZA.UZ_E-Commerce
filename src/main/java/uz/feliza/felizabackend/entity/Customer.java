package uz.feliza.felizabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends AbstractLongEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Temporal(TemporalType.DATE)
    private Date birthDate;
//    @Column(nullable = false)
//    private LocalDate birthDate;

    private Long saleSum; //975 000 50 000
    //1 000 000

    private Date lastSeen;

    @ManyToOne
    private Status status;

//    @ManyToMany
//    private List<Bonus> bonusList;

//    @ManyToMany
//    private List<Order> order;
}

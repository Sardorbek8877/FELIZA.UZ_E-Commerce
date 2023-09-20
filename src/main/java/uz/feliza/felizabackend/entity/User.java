package uz.feliza.felizabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbstractLongEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;
//  joinColumns = @JoinColumn(name = "user_id"),
//  inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles")
    private List<Role> roles;
}

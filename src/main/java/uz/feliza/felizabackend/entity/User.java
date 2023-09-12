//package uz.feliza.felizabackend.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import uz.feliza.felizabackend.entity.template.AbstractLongEntity;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity(name = "users")
//public class User extends AbstractLongEntity {
//
//    @Column(nullable = false)
//    private String fullName;
//
//    @Column(nullable = false)
//    private Date birthDate;
//
//    @Column(nullable = false, unique = true)
//    private String phoneNumber;
//
//    @Column(nullable = false)
//    private String password;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private List<Role> roles;
//}

package uz.feliza.felizabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import uz.feliza.felizabackend.entity.enums.RoleName;
import org.springframework.security.core.GrantedAuthority;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role extends AbstractLongEntity implements GrantedAuthority { // implements GrantedAuthority

    @Column(nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String description;

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    //    @Override
//    public String getAuthority() {
//        return roleName.name();
//    }
}

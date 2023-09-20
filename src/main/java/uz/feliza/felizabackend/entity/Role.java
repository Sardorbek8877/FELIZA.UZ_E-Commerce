package uz.feliza.felizabackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractLongEntity { // implements GrantedAuthority

    @Column(nullable = false)
    private String roleName;

//    @Override
//    public String getAuthority() {
//        return roleName.name();
//    }
}

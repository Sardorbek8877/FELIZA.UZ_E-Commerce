package uz.feliza.felizabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role extends AbstractLongEntity implements GrantedAuthority { // implements GrantedAuthority

    @Enumerated(value = EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return this.roleName.name();
    }
}

package uz.feliza.felizabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends AbstractLongEntity implements UserDetails {

    public Customer(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column//(nullable = false)
    @Size(min = 2, max = 50, message = "ism familiya 2 ta harfdan dan 50 ta harfgacha oraliqda bo'lishi kerak ")
    private String fullName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "telefon nomer kiriilishi kerak")
    private String phoneNumber;


//    @NotBlank(message = "parol kiritilishi kerak")
//    @NotNull()
    @Column( length = 64)
    private String password;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    private Long saleSum;

    private Date lastSeen;

    private String verifyCode;

    @ManyToOne
    private Status status;

    private boolean enabled = false;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private RoleName role = RoleName.CUSTOMER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

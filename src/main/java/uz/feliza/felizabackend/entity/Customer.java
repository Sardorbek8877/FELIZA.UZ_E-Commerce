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
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.entity.template.AbstractLongEntity;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends AbstractLongEntity implements UserDetails {

    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "ism familiya 2 ta harfdan dan 50 ta harfgacha oraliqda bo'lishi kerak ")
    private String fullName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "telefon nomer kiriilishi kerak")
    private String phoneNumber;

    @Column(length = 50, nullable = false, unique = true)
    @NaturalId(mutable = true)
    @NotBlank(message = "email address kiritilishi kerak")
    private String email;

    @Column(nullable = false, length = 64)
    @NotBlank(message = "parol kiritilishi kerak")
    @NotNull()
    private String password;

    @Temporal(TemporalType.DATE)
    private Date birthDate;
//    @Column(nullable = false)
//    private LocalDate birthDate;

    private Long saleSum; //975 000 50 000

    private Date lastSeen;

    @ManyToOne
    private Status status;

    private boolean enabled = false;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "customers_roles", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }



    @Override
    public String getUsername() {
        return email;
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

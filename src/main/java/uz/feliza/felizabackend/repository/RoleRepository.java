package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.feliza.felizabackend.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
}

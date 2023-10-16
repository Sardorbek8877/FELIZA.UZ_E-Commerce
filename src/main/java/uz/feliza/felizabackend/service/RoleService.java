package uz.feliza.felizabackend.service;

import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public void getOrCreateRole(RoleName roleName) {
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
        roleOptional.orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

    public boolean delete(Long id){
        try {
            roleRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

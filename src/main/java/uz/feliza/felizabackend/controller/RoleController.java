package uz.feliza.felizabackend.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping
    @RolesAllowed({"ADMIN"})
    public HttpEntity<?> getRoles(){
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/add")   // test comment for check server
    @RolesAllowed({"ADMIN"})
    public String addRole(){
        roleService.getOrCreateRole(RoleName.CUSTOMER);
        roleService.getOrCreateRole(RoleName.ADMIN);
        roleService.getOrCreateRole(RoleName.SALESPERSON);
        roleService.getOrCreateRole(RoleName.EDITOR);
        roleService.getOrCreateRole(RoleName.SHIPPER);
        roleService.getOrCreateRole(RoleName.ASSISTANT);
        return "Role lar yaratildi";
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        boolean deleted = roleService.delete(id);
        if (deleted)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}

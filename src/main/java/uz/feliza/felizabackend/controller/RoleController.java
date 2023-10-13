package uz.feliza.felizabackend.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping
    public HttpEntity<?> getRoles(){
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/addRole")
    public String addRole(){
        roleService.getOrCreateRole(RoleName.CUSTOMER);
        roleService.getOrCreateRole(RoleName.ADMIN);
        roleService.getOrCreateRole(RoleName.SALESPERSON);
        roleService.getOrCreateRole(RoleName.EDITOR);
        roleService.getOrCreateRole(RoleName.SHIPPER);
        roleService.getOrCreateRole(RoleName.ASSISTANT);
        return "Role lar yaratildi";
    }
}

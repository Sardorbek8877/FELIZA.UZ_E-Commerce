//package uz.feliza.felizabackend.role;
//
//import lombok.RequiredArgsConstructor;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import uz.feliza.felizabackend.entity.Role;
//import uz.feliza.felizabackend.repository.RoleRepository;
//
//import java.util.List;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//
//public class RoleRepositoryTest {
//
//    @Autowired
//    private  RoleRepository roleRepository;
//    @Test
//    public void testCreateFirstRole() {
//        Role roleAdmin = new Role("ROLE_ADMIN", "manage everything");
//        Role savedRole = roleRepository.save(roleAdmin);
//
//        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testCreateRestRoles() {
//        Role roleSalesperson = new Role("ROLE_SALESPERSON", "manage product price, customers, shipping, orders and sales report");
//        Role roleEditor = new Role("ROLE_EDITOR", "manage product price, customers, shipping, orders and sales report");
//        Role roleShipper = new Role("ROLE_SHIPPER", "view products, view orders and update order status");
//        Role roleAssistant = new Role("ROLE_ASSISTANT", "manage questions and reviews");
//
//        roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
//    }
//}

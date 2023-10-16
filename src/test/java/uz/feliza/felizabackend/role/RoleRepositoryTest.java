package uz.feliza.felizabackend.role;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class RoleRepositoryTest {

//    @Autowired
//    private  RoleRepository roleRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private TestEntityManager testEntityManager;

//    @Test
//    public void testUpdateCustomerRoleToAdmin(){
//        Optional<Customer> customer = customerRepository.findById(1L);
//        if (customer.isPresent()){
//            Customer customer1 = customer.get();
//            Role roleCustomer = new Role(1L);
//            Role roleAdmin = new Role(RoleName.ADMIN);
//
//            customer1.getRoles().remove(roleCustomer);
//            customer1.addRole(roleAdmin);
//
//            customerRepository.save(customer1);
//        }
//    }
}

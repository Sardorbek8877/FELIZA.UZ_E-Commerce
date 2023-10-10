package uz.feliza.felizabackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.VerificationToken;
import uz.feliza.felizabackend.exception.UserAlreadyExistsException;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.repository.RoleRepository;
import uz.feliza.felizabackend.repository.VerificationTokenRepository;
import uz.feliza.felizabackend.request.RegisterRequest;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository tokenRepository;
    @Override
    public Customer register(RegisterRequest request) {
        Optional<Customer> customer = this.findByEmail(request.getEmail());
        if (customer.isPresent()){
            throw new UserAlreadyExistsException(
                    "Customer with email " + request.getEmail() + " already exists!");
        }

        var newCustomer = new Customer();
        newCustomer.setFullName(request.getFullName());
        newCustomer.setEmail(request.getEmail());
        newCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
        newCustomer.setBirthDate(request.getBirthDate());
        newCustomer.setPhoneNumber(request.getPhoneNumber());

        Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        if (roleAdmin.isEmpty()){
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            Role savedRole = roleRepository.save(role);
            roleAdmin = Optional.of(savedRole);
        }
        newCustomer.setRoles(Collections.singleton(roleAdmin.get()));
        return customerRepository.save(newCustomer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public void saveCustomerVerificationToken(Customer customer, String token) {
        var verificationToken = new VerificationToken(token, customer);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null) return "Invalid verification token";

        Customer customer = token.getCustomer();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }

        customer.setEnabled(true);
        customerRepository.save(customer);
        return "valid";
    }
}

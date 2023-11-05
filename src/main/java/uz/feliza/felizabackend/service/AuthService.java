package uz.feliza.felizabackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.VerificationToken;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.exception.PasswordEmptyException;
import uz.feliza.felizabackend.exception.PhoneNumberAlreadyExistsException;
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
    public Customer registerAdmin(RegisterRequest request) {
        Optional<Customer> customer = this.findByEmail(request.getEmail());
        if (customer.isPresent()){
            throw new UserAlreadyExistsException(
                    "Admin with email " + request.getEmail() + " already exists!");
        }

        var newAdmin = new Customer();
        newAdmin.setFullName(request.getFullName());
        newAdmin.setEmail(request.getEmail());
        if(request.getPassword().isBlank()){
            throw new PasswordEmptyException("Admin password is empty!");
        }
        newAdmin.setPassword(passwordEncoder.encode(request.getPassword()));
        newAdmin.setBirthDate(request.getBirthDate());

        Optional<Customer> phoneNumber = customerRepository.findByPhoneNumber(request.getPhoneNumber());
        if (phoneNumber.isPresent())
            throw new PhoneNumberAlreadyExistsException(
                    "Admin with phone number " + request.getPhoneNumber() + " already exists!");
        newAdmin.setPhoneNumber(request.getPhoneNumber());

        Optional<Role> roleAdmin = roleRepository.findByRoleName(RoleName.ADMIN);
        if (roleAdmin.isEmpty()){
            Role role = new Role();
            role.setRoleName(RoleName.ADMIN);
            Role savedRole = roleRepository.save(role);
            roleAdmin = Optional.of(savedRole);
        }
        newAdmin.setRoles(Collections.singleton(roleAdmin.get()));
        return customerRepository.save(newAdmin);
    }

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
        if(request.getPassword().isBlank()){
              throw new PasswordEmptyException("Customer password is empty!");
        }
        newCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
        newCustomer.setBirthDate(request.getBirthDate());

        Optional<Customer> phoneNumber = customerRepository.findByPhoneNumber(request.getPhoneNumber());
        if (phoneNumber.isPresent())
            throw new PhoneNumberAlreadyExistsException(
                    "Customer with phone number " + request.getPhoneNumber() + " already exists!");
        newCustomer.setPhoneNumber(request.getPhoneNumber());

        Optional<Role> roleCustomer = roleRepository.findByRoleName(RoleName.CUSTOMER);
        if (roleCustomer.isEmpty()){
            Role role = new Role();
            role.setRoleName(RoleName.CUSTOMER);
            Role savedRole = roleRepository.save(role);
            roleCustomer = Optional.of(savedRole);
        }
        newCustomer.setRoles(Collections.singleton(roleCustomer.get()));
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

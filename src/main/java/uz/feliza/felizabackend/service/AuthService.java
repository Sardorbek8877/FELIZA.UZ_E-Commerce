package uz.feliza.felizabackend.service;

import lombok.NoArgsConstructor;
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
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.repository.RoleRepository;
import uz.feliza.felizabackend.repository.VerificationTokenRepository;
import uz.feliza.felizabackend.request.RegisterRequest;
import uz.feliza.felizabackend.sms.SendSmsService;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository tokenRepository;
    private final SendSmsService sendSmsService;

    public boolean isRegistered( String phoneNumber){
        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(phoneNumber);
        if (optionalCustomer.isPresent() && optionalCustomer.get().isEnabled())
            return true;

        String generatedRandomFourDigitNumber = sendSmsService.generateRandomFourDigitNumber();
        Customer customer = new Customer();
        customer.setPhoneNumber(phoneNumber);
        customer.setVerifyCode(generatedRandomFourDigitNumber);
        customerRepository.save(customer);

        String verifyCodeMessage = "Sizning tasdiqlash kodingiz: " + generatedRandomFourDigitNumber + ". Kodni begonalarga bermang!";
        sendSmsService.sendSmsToCustomer(phoneNumber, verifyCodeMessage);
        return false;
    }

    public ApiResponse register(RegisterRequest registerRequest){
        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(registerRequest.getPhoneNumber());
        if (optionalCustomer.isEmpty())
            return new ApiResponse("Bunday raqamli mijoz topilmadi", false);

        if (optionalCustomer.get().isEnabled())
            return new ApiResponse("Siz allaqachon ro'yxatdan o'tgansiz!", false);

        if (!registerRequest.getVerifyCode().equals(optionalCustomer.get().getVerifyCode()))
            return new ApiResponse("Tasdiqlash kodi noto'g'ri!", false);

        Customer newCustomer = optionalCustomer.get();
        newCustomer.setFullName(registerRequest.getFullName());
        newCustomer.setBirthDate(registerRequest.getBirthDate());
        newCustomer.setEnabled(true);

        if (registerRequest.getPassword().isBlank())
            throw new PasswordEmptyException("Password is empty!");
        newCustomer.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        customerRepository.save(newCustomer);

        return new ApiResponse("Siz muvaffaqiyatli ro'yxatdan o'tdingiz", true);
    }



    public Customer registerAdmin(RegisterRequest request) {
        Optional<Customer> customer = this.customerRepository.findByPhoneNumber(request.getPhoneNumber());
        if (customer.isPresent()){
            throw new UserAlreadyExistsException(
                    "Admin with email " + request.getPhoneNumber() + " already exists!");
        }

        var newAdmin = new Customer();
        newAdmin.setFullName(request.getFullName());
        newAdmin.setPhoneNumber(request.getPhoneNumber());
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
//        newAdmin.setRoles(Collections.singleton(roleAdmin.get()));
        return customerRepository.save(newAdmin);
    }

    public Customer registerDemo(RegisterRequest request) {
        Optional<Customer> customer = this.customerRepository.findByPhoneNumber(request.getPhoneNumber());
        if (customer.isPresent()){
            throw new UserAlreadyExistsException(
                    "Customer with email " + request.getPhoneNumber() + " already exists!");
        }

        var newCustomer = new Customer();
        newCustomer.setFullName(request.getFullName());
        newCustomer.setPhoneNumber(request.getPhoneNumber());
        if(request.getPassword().isBlank()){
              throw new PasswordEmptyException("Customer password is empty!");
        }
        newCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
        newCustomer.setBirthDate(request.getBirthDate());

//        Optional<Customer> phoneNumber = customerRepository.findByPhoneNumber(request.getPhoneNumber());
//        if (phoneNumber.isPresent())
//            throw new PhoneNumberAlreadyExistsException(
//                    "Customer with phone number " + request.getPhoneNumber() + " already exists!");
//        newCustomer.setPhoneNumber(request.getPhoneNumber());

        Optional<Role> roleCustomer = roleRepository.findByRoleName(RoleName.CUSTOMER);
        if (roleCustomer.isEmpty()){
            Role role = new Role();
            role.setRoleName(RoleName.CUSTOMER);
            Role savedRole = roleRepository.save(role);
            roleCustomer = Optional.of(savedRole);
        }
//        newCustomer.setRoles(Collections.singleton(roleCustomer.get()));
        return customerRepository.save(newCustomer);
    }

    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    public void saveCustomerVerificationToken(Customer customer, String token) {
        var verificationToken = new VerificationToken(token, customer);
        tokenRepository.save(verificationToken);
    }

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

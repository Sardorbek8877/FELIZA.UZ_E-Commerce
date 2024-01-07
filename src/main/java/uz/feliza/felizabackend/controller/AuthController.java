package uz.feliza.felizabackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.VerificationToken;
import uz.feliza.felizabackend.event.RegistrationCompleteEvent;
import uz.feliza.felizabackend.exception.UsernameNotFoundException;
import uz.feliza.felizabackend.payload.ApiResponse;
import uz.feliza.felizabackend.payload.AuthResponse;
import uz.feliza.felizabackend.request.LoginRequest;
import uz.feliza.felizabackend.request.PhoneNumberRequest;
import uz.feliza.felizabackend.request.RegisterRequest;
import uz.feliza.felizabackend.repository.VerificationTokenRepository;
import uz.feliza.felizabackend.service.AuthService;
import uz.feliza.felizabackend.util.JwtTokenUtil;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/isRegistered")
    public ResponseEntity<?> isRegistered(@RequestBody PhoneNumberRequest phoneNumberRequest){
        boolean registered = authService.isRegistered(phoneNumberRequest.getPhoneNumber());
        return ResponseEntity.ok(registered);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        ApiResponse apiResponse = authService.register(registerRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            Optional<Customer> customer = authService.findByPhoneNumber(loginRequest.getPhoneNumber());

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getPhoneNumber(), loginRequest.getPassword()));

            if (customer.isPresent()){
                Customer existingCustomer = (Customer) authenticate.getPrincipal();
                String accessToken = jwtTokenUtil.generateAccessToken(existingCustomer);
                AuthResponse response = new AuthResponse(existingCustomer.getPhoneNumber(),accessToken);
                return ResponseEntity.ok(response);
            }else
                throw new UsernameNotFoundException("Customer not found. Please, you should register first");
        }catch (BadCredentialsException | ParseException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }





    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody @Valid RegisterRequest registerRequest, final HttpServletRequest httpServletRequest){
        Customer customer = authService.registerAdmin(registerRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(customer,applicationUrl(httpServletRequest)));
        return "Success! Please, check your email to complete your registration";
    }
    @PostMapping("/regist")
    public String registerUser(@RequestBody @Valid RegisterRequest registerRequest, final HttpServletRequest httpServletRequest){
        Customer customer = authService.registerDemo(registerRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(customer,applicationUrl(httpServletRequest)));
        return "Success! Please, check your email to complete your registration";
    }


    @GetMapping("/register/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getCustomer().isEnabled())
            return "This account has already been verified. Please login!";
        String verificationResult = authService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid"))
            return "Email verified successfully. Now you can login to your account";
        return "Invalid verification token";
    }

    @GetMapping("/register/admin/verifyEmail")
    public String verifyEmailAdmin(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getCustomer().isEnabled())
            return "This account has already been verified. Please login!";
        String verificationResult = authService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid"))
            return "Email verified successfully. Now you can login to your account";
        return "Invalid verification token";
    }
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

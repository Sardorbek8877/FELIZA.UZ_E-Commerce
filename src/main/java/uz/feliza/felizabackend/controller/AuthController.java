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
import uz.feliza.felizabackend.payload.AuthResponse;
import uz.feliza.felizabackend.request.LoginRequest;
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

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegisterRequest registerRequest, final HttpServletRequest httpServletRequest){
        Customer customer = authService.register(registerRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(customer,applicationUrl(httpServletRequest)));
        return "Success! Please, check your email to complete your registration";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            Optional<Customer> customer = authService.findByEmail(loginRequest.getEmail());

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));

            if (customer.isPresent()){
                Customer existingCustomer = (Customer) authenticate.getPrincipal();
                String accessToken = jwtTokenUtil.generateAccessToken(existingCustomer);
                AuthResponse response = new AuthResponse(existingCustomer.getEmail(),accessToken);
                return ResponseEntity.ok(response);
            }else
                throw new UsernameNotFoundException("Customer not found. Please, you should register first");
        }catch (BadCredentialsException | ParseException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

package uz.feliza.felizabackend.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.enums.RoleName;
import uz.feliza.felizabackend.filter.JwtTokenFilter;
import uz.feliza.felizabackend.repository.CustomerRepository;
import uz.feliza.felizabackend.repository.UserRepository;
import uz.feliza.felizabackend.service.CustomCustomerDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final CustomerRepository customerRepository;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return new CustomCustomerDetailsService(customerRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exceptionHandlingConfigurer ->exceptionHandlingConfigurer.authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage()))
                ))

                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/api/auth/register/**")
                        .permitAll()
                        .requestMatchers("/api/auth/login","/api/**")
                        .permitAll()
                        .requestMatchers("/api/products/add").hasAnyAuthority("EDITOR","ADMIN")
                        .requestMatchers("/api/products").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/users/**").hasAnyAuthority("ROLE_CUSTOMER","ROLE_USER")
                        .requestMatchers("/api/customers").hasRole(RoleName.CUSTOMER.name())
                        .requestMatchers("/api/categories").hasAnyAuthority(RoleName.CUSTOMER.name())
                        .requestMatchers("/api/categories/add").hasAuthority(RoleName.ADMIN.name())
                        .requestMatchers("/api/categories/update").hasAuthority(RoleName.ADMIN.name())
                        .requestMatchers("/api/categories/delete").hasAuthority(RoleName.ADMIN.name())
                        .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

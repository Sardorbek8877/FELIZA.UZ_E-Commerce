package uz.feliza.felizabackend.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.feliza.felizabackend.entity.Customer;
import uz.feliza.felizabackend.entity.Role;
import uz.feliza.felizabackend.entity.User;
import uz.feliza.felizabackend.util.JwtTokenUtil;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!hasAuthorizationHeader(request)){
            filterChain.doFilter(request,response);
            return;
        }

        String accessToken = getAccessToken(request);

        if (!jwtTokenUtil.validateAccessToken(accessToken)){
            filterChain.doFilter(request,response);
            return;
        }

        setAuthenticationContext(accessToken, request);
        filterChain.doFilter(request,response);
    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(accessToken);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String accessToken) {
        Customer customerDetails = new Customer();
        Claims claims = jwtTokenUtil.parseClaims(accessToken);

        String claimRoles = (String) claims.get("roles");

        claimRoles = claimRoles.replace("[","").replace("]","");
        System.out.println("Claim roles: " + claimRoles);

        String[] rolesNames = claimRoles.split(",");

//        for (String aRoleName : rolesNames){
//            customerDetails.addRole(new Role(aRoleName));
//        }

        String subject = (String) claims.get(Claims.SUBJECT);
        String[] subjectArray = subject.split(",");

        customerDetails.setId(Long.parseLong(subjectArray[0]));
        customerDetails.setPhoneNumber(subjectArray[1]);
        return customerDetails;
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer");
    }

    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();
    }
}

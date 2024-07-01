package org.example.healthcare_management_system.login;

import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Service
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Replace with your UserDetailsService implementation

//    private final CustomUserDetailsService customUserDetailsService;
//
//    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println();
        System.out.println(email);
        System.out.println(password);
        System.out.println("in custom authentication provider");

        // Retrieve user details using your custom UserDetailsService
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        // Validate password (consider using a PasswordEncoder for secure comparison)
//        !password.equals(userDetails.getPassword())
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (email ==null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        System.out.println("validated");
        System.out.println("in custom authentication provider");
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getAuthorities());
        // Create a fully authenticated object with authorities (roles)
        Authentication NewUser = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        System.out.println(NewUser.getName());
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
//
//    private final CustomUserDetailsService customUserDetailsService;
//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;
//
//    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
////        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String email = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
//
//        if (passwordEncoder.matches(password, userDetails.getPassword())) {
//            // If password matches, return a fully authenticated Authentication object
//            return new CustomAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
//        } else {
//            // If password does not match, throw AuthenticationException
//            throw new BadCredentialsException("Invalid username or password");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authenticationType) {
//        // Indicates which authentication types this provider supports
//        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
//    }
}

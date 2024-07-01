package org.example.healthcare_management_system.login;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Component
public class AuthenticationConfig extends UsernamePasswordAuthenticationFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtUtil jwtUtil;

    public AuthenticationConfig(CustomUserDetailsService customUserDetailsService , CustomAuthenticationProvider customAuthenticationProvider ,  JwtUtil jwtUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println();
//        System.out.println("in attemptAuthentication ");
//        System.out.println(request);

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestBody = sb.toString();
//            System.out.println(requestBody);
//            System.out.println();
//            System.out.println("in attemptAuthentication ");

            // Parse the JSON data using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(requestBody);

            // Now you can access the JSON data
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();
//            String password = passwordEncoder.encode(jsonNode.get("password").asText());

//            String email = request.getParameter("email"); // Replace with your actual parameter name
//            String password = request.getParameter("password");

            System.out.println(email);
            System.out.println(password);
            System.out.println();
            System.out.println("in attemptAuthentication ");
            if (email == null || password == null) {
                throw new BadCredentialsException("Email or password is empty");
            }

            System.out.println("in attemptAuthentication 1");
            // Implement your custom email-based authentication logic here
            // (e.g., call your UserDetailsService with email instead of username)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            System.out.println("in attemptAuthentication 2");
            System.out.println(userDetails.getUsername());
            // UsernamePasswordAuthenticationToken expects username (use email here)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());

            System.out.println("in attemptAuthentication 3");
            setDetails(request, authenticationToken);
            return this.customAuthenticationProvider.authenticate(authenticationToken);
//            return authenticationToken;
//            return this.getAuthenticationManager().authenticate(authToken);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AuthenticationServiceException("Failed to parse authentication request body", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
//        // Set the authentication in the SecurityContext
//        System.out.println("in successfulAuthentication");
//        System.out.println(authResult);
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
//        System.out.println();
//        // You can add custom behavior here after successful authentication
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().write("Authentication Successful");
//        super.successfulAuthentication(request, response, chain, authResult);

        System.out.println();
        System.out.println("in successfulAuthentication");
//        System.out.println(authResult.getPrincipal());
//        System.out.println(authResult.getAuthorities());
//        System.out.println(authResult.getName());
//        System.out.println(authResult.getCredentials());
//        System.out.println(authResult.getDetails());
        String username =authResult.getName();
        System.out.println("in successfulAuthentication 1");
        String token = jwtUtil.generateToken(username);
        System.out.println();
        System.out.println("in successfulAuthentication");
        System.out.println(username);
        System.out.println(token);
        System.out.println();

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        // Handle unsuccessful authentication
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication Failed");
    }


}

//    @Autowired
//    public AuthenticationConfig(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }

//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;

//@Override
//protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                        Authentication authResult) throws IOException {
//    // Set the authentication in the SecurityContext
//    SecurityContextHolder.getContext().setAuthentication(authResult);
//
//    // You can add custom behavior here after successful authentication
//    response.setStatus(HttpServletResponse.SC_OK);
//    response.getWriter().write("Authentication Successful");
//}

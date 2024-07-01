package org.example.healthcare_management_system.controller;

import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")

public class UserDetailController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody Map<String, Object> updatedUserDetails) {
        try{
            String email = (String) updatedUserDetails.get("email");
            User user = customUserDetailsService.getUserDetailsById(email);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String currentPassword = (String)updatedUserDetails.get("currentPassword");

            System.out.println(currentPassword);
            System.out.println(user.getPassword());

            if(!passwordEncoder.matches(currentPassword, user.getPassword())){
                return ResponseEntity.status(500).body(null);
            }
//            if(currentPassword != user.getPassword()){
//                return ResponseEntity.status(500).body(null);
//            }

            user.setPassword(passwordEncoder.encode((String)updatedUserDetails.get("newPassword")));
            customUserDetailsService.saveUserDetails(user);
            return ResponseEntity.ok(user);
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/settings")
    public ResponseEntity<User> settings(@RequestBody Map<String, Object> updatedUserDetails) {
        try{
            String email = (String) updatedUserDetails.get("email");
            User user = customUserDetailsService.getUserDetailsById(email);

            String name = (String) updatedUserDetails.get("name");
            String[] words = name.split(" ");
            String firstName = words[0];
            String lastName ="";
            for (int i=1 ; i<words.length ; i++){
                lastName+=" "+words[i];
            }

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setGender((String) updatedUserDetails.get("gender"));
            user.setAddress((String) updatedUserDetails.get("address"));
            user.setMobileNumber((String) updatedUserDetails.get("mobileNumber"));

            String dob = (String)updatedUserDetails.get("dob");
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String changeFormatDOB = "";
            changeFormatDOB = changeFormatDOB + dob.charAt(8)+dob.charAt(9)+"/"+ dob.charAt(5)+dob.charAt(6)+"/"
            + dob.charAt(0)+dob.charAt(1)+dob.charAt(2)+dob.charAt(3);

            user.setDob(LocalDate.parse(changeFormatDOB , formatterDate));
            System.out.println(changeFormatDOB);
            customUserDetailsService.saveUserDetails(user);
            return ResponseEntity.ok(user);
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User userdetails) {
        try{
            System.out.println(userdetails);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userdetails.setPassword(passwordEncoder.encode(userdetails.getPassword()));
//            System.out.println("maibee hashed password");
//            System.out.println(userdetails.getPassword());
            customUserDetailsService.saveUserDetails(userdetails);
            return ResponseEntity.ok(userdetails);
        }
        catch(Exception e){
            System.out.println();
            System.out.println(e);
            System.out.println("printing error");
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/currentLoginUser")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String LoginUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(LoginUserEmail);
        User LoginUserDetails = getUserById(LoginUserEmail);
        System.out.println();
        System.out.println("authentication");
        System.out.println(authentication);
        System.out.println("in get current user");
        System.out.println(authentication.getPrincipal());
        System.out.println(LoginUserDetails);
        return LoginUserDetails;
    }

//    @GetMapping("/currentLoginUser")
//    public Authentication getCurrentUser() {
//        String LoginUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        return SecurityContextHolder.getContext().getAuthentication();
//    }

    @GetMapping("/findByAuthority")
    public List<User> findByAuthority(){
        return customUserDetailsService.findByAuthority();
    }

    @GetMapping("/getAllPatients")
    public List<User> getAllPatients(){
        return customUserDetailsService.getAllPatients();
    }

    @GetMapping("/seeAllDoctors")
    public List<Map<String ,Object>> seeAllDoctors() {
        return customUserDetailsService.seeAllDoctors();
    }


    @GetMapping("/getAllDoctorsDegrees")
    public List<Map<String ,Object>> getAllDoctorsDegrees() {
        return customUserDetailsService.getAllDoctorsDegrees();
    }


    @GetMapping("/getAllDoctorsSpecialities")
    public List<Map<String ,Object>> getAllDoctorsSpecialities() {
        return customUserDetailsService.getAllDoctorsSpecialities();
    }

    @GetMapping
    public List<User> getAllUsers() {
        return customUserDetailsService.getAllUsersDetails();
    }

    @GetMapping("/{email}")
    public User getUserById(@PathVariable String email) {
        User userDetailOptional = customUserDetailsService.getUserDetailsById(email);
        return userDetailOptional; // Or handle the case when user is not found
    }

    @PutMapping("/{email}")
    public User updateUserDetail(@PathVariable String email, @RequestBody User userDetails) {
        return customUserDetailsService.updateUserDetails(email, userDetails);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUserDetail(@RequestParam(name="email") String email) {
        customUserDetailsService.deleteUserDetails(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getPatientsFromDoctor")
    public List<Map<String, Object>> getPatientsFromDoctor(@RequestParam(name="doctorId") String doctorId){
//        System.out.println(doctorId);
//        System.out.println("getPatientsFromDoctor controller");
        return customUserDetailsService.getPatientsFromDoctor(doctorId);
    }
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        if (authentication != null) {
//            new SecurityContextLogoutHandler().logout(request, response, authentication);
//        }
//        return "redirect:/login"; // Redirect to the login page after logout
//    }
}
package org.example.healthcare_management_system.service;
import org.example.healthcare_management_system.model.CustomUserDetails;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;
//    private final UserDetailRepository userDetailRepository;
//
//    public CustomUserDetailsService(UserDetailRepository userRepository) {
//        this.userDetailRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDetailRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }

    public User saveUserDetails(User user) {

        return userDetailRepository.save(user);
    }

    public List<User> getAllUsersDetails() {
        return userDetailRepository.findAll();
    }

    public User getUserDetailsById(String email) throws UsernameNotFoundException {
        return userDetailRepository.findById(email).orElse(null);
//        return userDetailRepository.findById(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        Optional<UserDetails> user = userDetailRepository.findById(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found!");
//        }
//        return user;
    }

    public User updateUserDetails(String email, User user ) {
        if (userDetailRepository.existsById(email)) {
            user.setEmail(email); // Ensure the provided user has the correct ID
            return userDetailRepository.save(user);
        }
        return null; // Or throw an exception indicating the user was not found
    }

    public void deleteUserDetails(String email) {
        userDetailRepository.deleteById(email);
    }

    public List<User> findByAuthority() {
        System.out.println(userDetailRepository.findByAuthority());
        return userDetailRepository.findByAuthority();
    }

    public List<User> getAllPatients() {
        return userDetailRepository.getAllPatients();
    }

    public List<Map<String , Object>> seeAllDoctors() {
        System.out.println(userDetailRepository.getAllDoctors());
        return userDetailRepository.getAllDoctors();
    }

    public List<Map<String ,Object>> getAllDoctorsDegrees() {
        System.out.println(userDetailRepository.getAllDoctorsDegrees());
        return userDetailRepository.getAllDoctorsDegrees();
    }

    public List<Map<String ,Object>> getAllDoctorsSpecialities() {
        System.out.println(userDetailRepository.getAllDoctorsSpecialities());
        return userDetailRepository.getAllDoctorsSpecialities();
    }

    public List<Map<String , Object>> getPatientsFromDoctor(String doctorId){
        System.out.println(doctorId);
        System.out.println("getPatientsFromDoctor service");
        System.out.println( userDetailRepository.getPatientsFromDoctor(doctorId));
        System.out.println();
        List<Map<String ,Object>> l= userDetailRepository.getPatientsFromDoctor(doctorId);
        System.out.println(l.size());
        for(int i=0 ; i<l.size(); i++){
            System.out.println(l.get(i).get("email"));
        }
        return userDetailRepository.getPatientsFromDoctor(doctorId);
    }

//    public List<User> getUsersByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
}

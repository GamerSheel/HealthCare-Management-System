//package org.example.healthcare_management_system.service;
//
//import org.example.healthcare_management_system.model.User;
//import org.example.healthcare_management_system.repository.UserDetailRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserDetailService implements UserDetailsService {
//    @Autowired
//    private UserDetailRepository userDetailRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        // 1. Retrieve user data based on username (from your data source)
//        User user = userRepository.findByUsername(email); // Assuming a UserRepository
//
//        // 2. Check if user exists
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found!");
//        }
//
//        // 3. Convert user data to UserDetails object
//        return new User(user); // Your custom UserDetails implementation
//    }
//
//    public User saveUserDetails(User user) {
//        return userDetailRepository.save(user);
//    }
//
//    public List<User> getAllUsersDetails() {
//        return userDetailRepository.findAll();
//    }
//
//    public Optional<User> getUserDetailsById(String email) throws UsernameNotFoundException {
//        return userDetailRepository.findById(email);
////        return userDetailRepository.findById(email)
////                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
////        Optional<UserDetails> user = userDetailRepository.findById(email);
////        if (user == null) {
////            throw new UsernameNotFoundException("User not found!");
////        }
////        return user;
//    }
//
//    public User updateUserDetails(String email, User user ) {
//        if (userDetailRepository.existsById(email)) {
//            user.setEmail(email); // Ensure the provided user has the correct ID
//            return userDetailRepository.save(user);
//        }
//        return null; // Or throw an exception indicating the user was not found
//    }
//
//    public void deleteUserDetails(String email) {
//
//        userDetailRepository.deleteById(email);
//    }
////    public List<User> getUsersByUsername(String username) {
////        return userRepository.findByUsername(username);
////    }
//}

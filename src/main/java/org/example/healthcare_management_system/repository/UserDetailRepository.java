package org.example.healthcare_management_system.repository;
//import org.example.healthcare_management_system.model.CustomUserDetails;
import org.example.healthcare_management_system.model.User;
//import org.example.healthcare_management_system.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<User, String> {
//    @Query("SELECT u FROM User u WHERE u.username = :username")
//    List<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void deleteById(String email);

    @Query ("SELECT u from User u where u.authority='ROLE_DOCTOR'")
    List<User> findByAuthority();

    @Query("SELECT patient FROM User patient WHERE patient.authority='ROLE_PATIENT'")
    List<User> getAllPatients();

    @Query("SELECT doctor.email email, doctor.firstName firstName , doctor.lastName lastName FROM User doctor WHERE doctor.authority='ROLE_DOCTOR'")
    List<Map<String , Object>> getAllDoctors();

    @Query("SELECT doctor.email email, doctorDegree.degree degree, doctorDegree.institute institute, doctorDegree.year  yearDegree FROM DoctorDegree doctorDegree " +
            "JOIN User doctor ON doctor.email = doctorDegree.doctorId.email WHERE  doctor.authority='ROLE_DOCTOR'"
    )
    List<Map<String ,Object>> getAllDoctorsDegrees();

    @Query("SELECT doctor.email email, doctorSpeciality.speciality speciality, doctorSpeciality.FeesAmount FeesAmount FROM DoctorSpeciality doctorSpeciality " +
            "JOIN User doctor ON doctor.email = doctorSpeciality.doctorId.email WHERE doctor.authority='ROLE_DOCTOR'"
    )
    List<Map<String ,Object>> getAllDoctorsSpecialities();


    @Query("SELECT DISTINCT patient.email email , patient.firstName firstName, patient.lastName lastName, patient.gender gender , patient.mobileNumber mobileNumber FROM User patient JOIN Appointment app ON patient.email = app.patientId.email WHERE app.doctorId.email = :doctorId")
    List<Map<String , Object>> getPatientsFromDoctor(@Param("doctorId") String doctorId);


}


//    @Query(
//            "SELECT doctorDegree.degree , doctorDegree.institute , doctorDegree.year ,"+
//            "doctorSpeciality.speciality , doctorSpeciality.FeesAmount , "  +
//            "doctor.firstName , doctor.lastName , doctor.gender" +
//                    "FROM DoctorDegree doctorDegree " +
//                    "JOIN DoctorSpeciality doctorSpeciality ON doctorDegree.doctorId = doctorSpeciality.doctoId"+
//                    "JOIN User doctor ON doctor.email = doctorDegree.doctorId"+
//            "WHERE  doctor.authority='ROLE_DOCTOR'"
//    )
//
//    List<Object[]> getAllDoctors();

//In the repository file, you only need to define an interface that extends JpaRepository and
// specifies the entity type (User) and the type of the primary key (Long).
// Spring Data JPA will automatically provide implementations for basic CRUD operations based on the methods defined in this interface.

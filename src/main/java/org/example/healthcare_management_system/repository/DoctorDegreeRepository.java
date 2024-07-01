package org.example.healthcare_management_system.repository;
import org.example.healthcare_management_system.model.Appointment;
import org.example.healthcare_management_system.model.DoctorDegree;
import org.example.healthcare_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


public interface DoctorDegreeRepository extends JpaRepository<DoctorDegree, UUID> {

    @Query("SELECT degree FROM DoctorDegree degree WHERE degree.doctorId.email=:doctorId")
    public List<DoctorDegree> getDegreeByDoctorId(@Param("doctorId") String doctorId );

}

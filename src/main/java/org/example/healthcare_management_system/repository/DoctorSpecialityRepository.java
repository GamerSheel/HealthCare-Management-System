package org.example.healthcare_management_system.repository;
import org.example.healthcare_management_system.model.DoctorDegree;
import org.example.healthcare_management_system.model.DoctorSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DoctorSpecialityRepository extends JpaRepository<DoctorSpeciality, UUID> {
    @Query("SELECT speciality FROM DoctorSpeciality speciality WHERE speciality.doctorId.email=:doctorId")
    public List<DoctorSpeciality> getSpecialityByDoctorId(@Param("doctorId") String doctorId );

}

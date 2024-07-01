package org.example.healthcare_management_system.repository;
import org.example.healthcare_management_system.model.Appointment;
import org.example.healthcare_management_system.model.PatientRecord;
import org.example.healthcare_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, UUID> {
    @Query("SELECT r FROM PatientRecord r WHERE r.appointmentId= :appointmentId  ")
    PatientRecord getPatientRecordByAppointmentId(@Param("appointmentId") Appointment appointmentId );
}

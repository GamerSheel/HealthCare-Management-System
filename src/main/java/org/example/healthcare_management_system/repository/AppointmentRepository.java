package org.example.healthcare_management_system.repository;
import org.example.healthcare_management_system.model.Appointment;
import org.example.healthcare_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a WHERE a.patientId= :patientId AND a.appointmentDate> :todayDate OR(a.appointmentDate=:todayDate AND a.endTime>= :currentTime)  ")
    List<Appointment> getAllUpcomingAppointmentsOfPatientById(@Param("patientId") User patientId , @Param("todayDate") LocalDate todayDate , @Param("currentTime") LocalTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.doctorId= :doctorId AND a.appointmentDate> :todayDate OR(a.appointmentDate=:todayDate AND a.endTime>= :currentTime)  ")
    List<Appointment> getAllUpcomingAppointmentsOfDoctorById(@Param("doctorId") User doctorId , @Param("todayDate") LocalDate todayDate , @Param("currentTime") LocalTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate> :todayDate OR(a.appointmentDate=:todayDate AND a.endTime>= :currentTime)  ")
    List<Appointment> getAllUpcomingAppointments( @Param("todayDate") LocalDate todayDate , @Param("currentTime") LocalTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.patientId= :patientId AND a.appointmentDate< :todayDate or(a.appointmentDate= :todayDate AND a.endTime< :currentTime)")
    List<Appointment> getAllPreviousAppointmentsOfPatientById(@Param("patientId") User patientId , @Param("todayDate") LocalDate todayDate , @Param("currentTime") LocalTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.doctorId= :doctorId AND a.appointmentDate< :todayDate or(a.appointmentDate= :todayDate AND a.endTime< :currentTime)")
    List<Appointment> getAllPreviousAppointmentsOfDoctorById(@Param("doctorId") User doctorId , @Param("todayDate") LocalDate todayDate , @Param("currentTime") LocalTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate< :todayDate or(a.appointmentDate= :todayDate AND a.endTime< :currentTime)")
    List<Appointment> getAllPreviousAppointments( @Param("todayDate") LocalDate todayDate , @Param("currentTime") LocalTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.doctorId.email = :doctorId AND a.patientId.email =:patientId")
    List<Appointment> getAllAppointmentsPatient(@Param("doctorId") String doctorId , @Param("patientId") String patientId );

}

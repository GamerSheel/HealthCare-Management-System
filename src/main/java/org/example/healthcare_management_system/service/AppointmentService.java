package org.example.healthcare_management_system.service;

import org.example.healthcare_management_system.model.Appointment;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public List<Appointment> getAllUpcomingAppointmentsOfPatientById(String patientId){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = formatterDate.format(LocalDate.now());
        LocalDate todayDate =  LocalDate.parse(currentDate , formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String nowTime = formatterTime.format(LocalTime.now());
        LocalTime currentTime = LocalTime.parse(nowTime, formatterTime);
        System.out.println();
        System.out.println("in service of upcoming appointment");

        User patient = customUserDetailsService.getUserDetailsById(patientId);

        return appointmentRepository.getAllUpcomingAppointmentsOfPatientById(patient , todayDate , currentTime);
    }


    public List<Appointment> getAllUpcomingAppointmentsOfDoctorById(String doctorId){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = formatterDate.format(LocalDate.now());
        LocalDate todayDate =  LocalDate.parse(currentDate , formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String nowTime = formatterTime.format(LocalTime.now());
        LocalTime currentTime = LocalTime.parse(nowTime, formatterTime);
        System.out.println();
        System.out.println("in service of upcoming appointment");

        User doctor = customUserDetailsService.getUserDetailsById(doctorId);

        return appointmentRepository.getAllUpcomingAppointmentsOfDoctorById(doctor , todayDate , currentTime);
    }

    public List<Appointment> getAllUpcomingAppointments(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = formatterDate.format(LocalDate.now());
        LocalDate todayDate =  LocalDate.parse(currentDate , formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String nowTime = formatterTime.format(LocalTime.now());
        LocalTime currentTime = LocalTime.parse(nowTime, formatterTime);

        return appointmentRepository.getAllUpcomingAppointments( todayDate , currentTime);
    }

    public List<Appointment> getAllPreviousAppointmentsOfPatientById(String patientId){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = formatterDate.format(LocalDate.now());
        LocalDate todayDate =  LocalDate.parse(currentDate , formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String nowTime = formatterTime.format(LocalTime.now());
        LocalTime currentTime = LocalTime.parse(nowTime, formatterTime);
        System.out.println();
        System.out.println("in service of upcoming appointment");

        User patient = customUserDetailsService.getUserDetailsById(patientId);

        return appointmentRepository.getAllPreviousAppointmentsOfPatientById(patient , todayDate , currentTime);
    }

    public List<Appointment> getAllPreviousAppointmentsOfDoctorById(String doctorId){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = formatterDate.format(LocalDate.now());
        LocalDate todayDate =  LocalDate.parse(currentDate , formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String nowTime = formatterTime.format(LocalTime.now());
        LocalTime currentTime = LocalTime.parse(nowTime, formatterTime);
        System.out.println();
        System.out.println("in service of upcoming appointment");

        User doctor = customUserDetailsService.getUserDetailsById(doctorId);
        return appointmentRepository.getAllPreviousAppointmentsOfDoctorById(doctor , todayDate , currentTime);
    }

    public List<Appointment> getAllPreviousAppointments(){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = formatterDate.format(LocalDate.now());
        LocalDate todayDate =  LocalDate.parse(currentDate , formatterDate);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String nowTime = formatterTime.format(LocalTime.now());
        LocalTime currentTime = LocalTime.parse(nowTime, formatterTime);

        return appointmentRepository.getAllPreviousAppointments( todayDate , currentTime);
    }

    public List<Appointment>getAllAppointmentsPatient(String patientId, String doctorId){
        return appointmentRepository.getAllAppointmentsPatient(doctorId , patientId);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(UUID appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    public Appointment updateAppointment(UUID appointmentId, Appointment appointment ) {
        if (appointmentRepository.existsById(appointmentId)) {
            appointment.setAppointmentId(appointmentId); // Ensure the provided user has the correct ID
            return appointmentRepository.save(appointment);
        }
        return null; // Or throw an exception indicating the user was not found
    }

    public void deleteAppointment(UUID appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

}

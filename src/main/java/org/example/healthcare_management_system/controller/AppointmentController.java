package org.example.healthcare_management_system.controller;

import org.example.healthcare_management_system.model.Appointment;
import org.example.healthcare_management_system.model.DoctorDegree;
import org.example.healthcare_management_system.model.PatientRecord;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.service.AppointmentService;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/bookAppointment")
    public Appointment createAppointment(@RequestBody Map<String, Object> appointmentForm) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

//        String appointmentDate = (String)appointmentForm.get("appointmentDate");
//
//        String changeFormatAppointmentDate = "";
//        changeFormatAppointmentDate = changeFormatAppointmentDate + appointmentDate.charAt(8)+appointmentDate.charAt(9)+"/"+ appointmentDate.charAt(5)+appointmentDate.charAt(6)+"/"
//         + appointmentDate.charAt(0)+appointmentDate.charAt(1)+appointmentDate.charAt(2)+appointmentDate.charAt(3);
//
//        System.out.println(changeFormatAppointmentDate);
//        appointment.setAppointmentDate(LocalDate.parse(changeFormatAppointmentDate , formatterDate)  );
//


        Appointment appointment = new Appointment();

        appointment.setIssue((String) appointmentForm.get("issue"));
        appointment.setStatus((String) appointmentForm.get("status"));
        appointment.setForWhichPerson((String) appointmentForm.get("forWhichPerson"));
        appointment.setAge((String) appointmentForm.get("age"));

        UUID uuid = UUID.randomUUID();
        appointment.setAppointmentId(uuid);

        String appointmentDate = (String)appointmentForm.get("appointmentDate");
        appointment.setAppointmentDate(LocalDate.parse(appointmentDate , formatterDate));

        String startTime = (String)appointmentForm.get("startTime");
        appointment.setStartTime(LocalTime.parse(startTime , formatterTime) );
        String endTime = (String)appointmentForm.get("endTime");
        appointment.setEndTime(LocalTime.parse(endTime , formatterTime) );

        String doctorEmail = (String)appointmentForm.get("doctorId");
        User userDoctor=customUserDetailsService.getUserDetailsById(doctorEmail);
        appointment.setDoctorId(userDoctor);

        String patientEmail = (String)appointmentForm.get("patientId");
        User userPatient = customUserDetailsService.getUserDetailsById(patientEmail);
        appointment.setPatientId(userPatient);


        return appointmentService.saveAppointment(appointment);
    }

    @GetMapping("/UpcomingPatientId")
    public List<Appointment> getAllUpcomingAppointmentsOfPatientById(@RequestParam(name="patientId") String patientId){
        System.out.println();
        System.out.println("in get All Upcoming Appointments Of Patient By Id");
        System.out.println(patientId);
        return appointmentService.getAllUpcomingAppointmentsOfPatientById(patientId);
    }

    @GetMapping("/UpcomingDoctorId")
    public List<Appointment> getAllUpcomingAppointmentsOfDoctorById(@RequestParam(name="doctorId") String doctorId){
        System.out.println();
        System.out.println("in get All Upcoming Appointments Of Doctor By Id");
        System.out.println(doctorId);
        return appointmentService.getAllUpcomingAppointmentsOfDoctorById(doctorId);
    }

    @GetMapping("/allUpcomingAppointments")
    public List<Appointment> getAllUpcomingAppointments(){
        return appointmentService.getAllUpcomingAppointments();
    }

    @GetMapping("/PreviousPatientId")
    public List<Appointment> getAllPreviousAppointmentsOfPatientById(@RequestParam(name="patientId") String patientId){
        System.out.println();
        System.out.println("in get All Previous Appointments Of Patient By Id");
        System.out.println(patientId);
        return appointmentService.getAllPreviousAppointmentsOfPatientById(patientId);
    }

    @GetMapping("/PreviousDoctorId")
    public List<Appointment> getAllPreviousAppointmentsOfDoctorById(@RequestParam(name="doctorId") String doctorId){
        System.out.println();
        System.out.println("in get All Previous Appointments Of Patient By Id");
        System.out.println(doctorId);
        return appointmentService.getAllPreviousAppointmentsOfDoctorById(doctorId);
    }

    @GetMapping("/allPreviousAppointments")
    public List<Appointment> getAllPreviousAppointments(){
        return appointmentService.getAllPreviousAppointments();
    }

    @GetMapping("/getAllAppointmentsPatient")
    public List<Appointment>getAllAppointmentsPatient(@RequestParam(name="patientId")String patientId, @RequestParam(name="doctorId")String doctorId){
        System.out.println();
        System.out.println(patientId);
        System.out.println(doctorId);
        System.out.println("in controller getAllAppointmentsPatient");
        System.out.println();
        return appointmentService.getAllAppointmentsPatient(patientId , doctorId );
    }

    @PostMapping
    public Appointment saveAppointment(@RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{appointmentId}")
    public Appointment getAppointmentById(@PathVariable UUID appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(appointmentId);
        return appointmentOptional.orElse(null); // Or handle the case when user is not found
    }

    @PutMapping("/{appointmentId}")
    public Appointment updateAppointment(@PathVariable UUID appointmentId, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(appointmentId, appointment);
    }

    @DeleteMapping("/{appointmentId}")
    public void deleteAppointment(@PathVariable UUID appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }
}
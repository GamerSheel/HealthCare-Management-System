package org.example.healthcare_management_system.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import jakarta.servlet.annotation.MultipartConfig;
import org.example.healthcare_management_system.model.Appointment;
import org.example.healthcare_management_system.model.PatientRecord;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.service.AppointmentService;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.example.healthcare_management_system.service.PatientRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/patientRecord")
@CrossOrigin(origins = "http://localhost:3000/")
@MultipartConfig
public class PatientRecordController {

    @Autowired
    private PatientRecordService patientRecordService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AppointmentService appointmentService;
    private final String storageLocation = "C:\\HMS_SpringBoot";


    @PostMapping("/save")
    public PatientRecord createPatientRecord(
//            @RequestBody Map<String, Object> patientRecordForm
            @RequestParam("description") String description,
            @RequestParam("notes") String notes,
            @RequestParam("prescription") String prescription,
            @RequestParam("doctorId") String doctorEmail,
            @RequestParam("patientId") String patientEmail,
            @RequestParam("appointmentId") String appointmentIdStr,
            @RequestParam("medicalRecordUploadPath") MultipartFile file
    ) throws Exception {
        System.out.println();
        System.out.println("saving patient record working fine 0");

        PatientRecord patientRecord = new PatientRecord();

        LocalDateTime today = LocalDateTime.now();
        String pattern = "dd-MM-yyyy HH:mm:ss.SSS";  // "dd-MM-yyyy HH:mm:ss.SSS"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDate = today.format(formatter);
        LocalDateTime parsedDate = LocalDateTime.parse(formattedDate, formatter);

        patientRecord.setRecordDate(parsedDate);

        patientRecord.setDescription(description);
        patientRecord.setNotes(notes);
        patientRecord.setPrescription(prescription);
//        patientRecord.setDescription((String) patientRecordForm.get("description"));
//        patientRecord.setNotes((String) patientRecordForm.get("notes"));
////        patientRecord.setMedicalRecordUploadPath((String) patientRecordForm.get("medicalRecordUploadPath"));
//        patientRecord.setPrescription((String)patientRecordForm.get("prescription"));

//        String doctorEmail = (String)patientRecordForm.get("doctorId");
        User userDoctor=customUserDetailsService.getUserDetailsById(doctorEmail);
        patientRecord.setDoctorId(userDoctor);

//        String patientEmail = (String)patientRecordForm.get("patientId");
        User userPatient = customUserDetailsService.getUserDetailsById(patientEmail);
        patientRecord.setPatientId(userPatient);

        UUID appointmentId = UUID.fromString(appointmentIdStr);
//        UUID appointmentId = UUID.fromString ((String)patientRecordForm.get("appointmentId"));
        Appointment appointment = appointmentService.getAppointmentById(appointmentId).orElse(null);
        patientRecord.setAppointmentId(appointment);

        System.out.println();
        System.out.println("saving patient record working fine 1");


//        MultipartFile file = (MultipartFile) patientRecordForm.get("medicalRecordUploadPath");
        String uniqueFileName = appointmentId.toString() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(storageLocation, uniqueFileName);
        Files.write(filePath, file.getBytes());
        patientRecord.setMedicalRecordUploadPath(filePath.toString());

//        File dest = new File(storageLocation + "/" + file.getOriginalFilename());
//        file.transferTo(dest);
//        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
//

        System.out.println();
        System.out.println("saving patient record working fine 2");

        if(patientRecordService.getPatientRecordByAppointmentId(appointment)!=null){
            PatientRecord existingPatientRecord = patientRecordService.getPatientRecordByAppointmentId(appointment);
            patientRecord.setPatientRecordId(existingPatientRecord.getPatientRecordId());
        }else{
            UUID uuid = UUID.randomUUID();
            patientRecord.setPatientRecordId(uuid);
        }

        System.out.println();
        System.out.println("saving patient record working fine 3");
        return patientRecordService.savePatientRecord(patientRecord);
    }

    @GetMapping("/ByAppointmentId")
    public PatientRecord getPatientRecordByAppointmentId(@RequestParam(name="appointmentId") String appointmentIdString){
        UUID appointmentId = UUID.fromString(appointmentIdString);
        Appointment appointment = appointmentService.getAppointmentById(appointmentId).orElse(null);
        return patientRecordService.getPatientRecordByAppointmentId(appointment);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(name="fileName") String filename , @RequestParam(name="appointmentId") String appointmentId) {
        try {
            System.out.println(filename);
            System.out.println(appointmentId);
            String fullFileName = appointmentId + "_" + filename;
            System.out.println("next line 1");
            Path filePath = Paths.get(storageLocation, fullFileName);
            System.out.println(filePath);
            System.out.println("next line 2");
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("next line 3");
            System.out.println(resource.exists());

            if (!resource.exists() || !resource.isReadable()) {
                System.out.println("File not found or not readable: " + filename);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public List<PatientRecord> getAllPayments() {
        return patientRecordService.getAllPatientRecords();
    }

    @GetMapping("/{patientRecordId}")
    public PatientRecord getPatientRecordById(@PathVariable UUID patientRecordId) {
        Optional<PatientRecord> patientRecordOptional = patientRecordService.getPatientRecordById(patientRecordId);
        return patientRecordOptional.orElse(null); // Or handle the case when user is not found
    }

    @PutMapping("/{patientRecordId}")
    public PatientRecord updatePatientRecord(@PathVariable UUID patientRecordId, @RequestBody PatientRecord patientRecord) {
        return patientRecordService.updatePatientRecord(patientRecordId, patientRecord);
    }

    @DeleteMapping("/{patientRecordId}")
    public void deletePatientRecord(@PathVariable UUID patientRecordId) {
        patientRecordService.deletePatientRecord(patientRecordId);
    }
}
package org.example.healthcare_management_system.service;

import org.example.healthcare_management_system.model.Appointment;
import org.example.healthcare_management_system.model.PatientRecord;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.repository.PatientRecordRepository;
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
public class PatientRecordService {
    @Autowired
    private PatientRecordRepository patientRecordRepository;

    public PatientRecord savePatientRecord(PatientRecord patientRecord) {
        return patientRecordRepository.save(patientRecord);
    }

    public List<PatientRecord> getAllPatientRecords() {
        return patientRecordRepository.findAll();
    }

    public Optional<PatientRecord> getPatientRecordById(UUID patientRecordId) {
        return patientRecordRepository.findById(patientRecordId);
    }

    public PatientRecord updatePatientRecord(UUID patientRecordId, PatientRecord patientRecord ) {
        if (patientRecordRepository.existsById(patientRecordId)) {
            // Ensure the provided user has the correct ID
            return patientRecordRepository.save(patientRecord);
        }
        return null; // Or throw an exception indicating the user was not found
    }

    public void deletePatientRecord(UUID patientRecordId) {
        patientRecordRepository.deleteById(patientRecordId);
    }

    public PatientRecord getPatientRecordByAppointmentId(Appointment appointmentId){
        return patientRecordRepository.getPatientRecordByAppointmentId(appointmentId);
    }
}

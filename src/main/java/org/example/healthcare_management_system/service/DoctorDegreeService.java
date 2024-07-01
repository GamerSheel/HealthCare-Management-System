package org.example.healthcare_management_system.service;
import org.example.healthcare_management_system.model.DoctorDegree;
import org.example.healthcare_management_system.repository.DoctorDegreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorDegreeService {
    @Autowired
    private DoctorDegreeRepository doctorDegreeRepository;

    public List<DoctorDegree> getDegreeByDoctorId(String doctorId) {
        return doctorDegreeRepository.getDegreeByDoctorId(doctorId);
    }

    public DoctorDegree saveDoctorDegree(DoctorDegree doctorDegree) {

        return doctorDegreeRepository.save(doctorDegree);
    }

    public List<DoctorDegree> getAllDoctorDegrees() {
        return doctorDegreeRepository.findAll();
    }

    public Optional<DoctorDegree> getDoctorDegreeById(UUID degreeId) {
        return doctorDegreeRepository.findById(degreeId);
    }

    public DoctorDegree updateDoctorDegree(UUID degreeId, DoctorDegree doctorDegree ) {
        if (doctorDegreeRepository.existsById(degreeId)) {
            doctorDegree.setDegreeId(degreeId); // Ensure the provided user has the correct ID
            return doctorDegreeRepository.save(doctorDegree);
        }
        return null; // Or throw an exception indicating the user was not found
    }

    public void deleteDoctorDegree(UUID degreeId) {
        doctorDegreeRepository.deleteById(degreeId);
    }

}

package org.example.healthcare_management_system.service;

import org.example.healthcare_management_system.model.DoctorSpeciality;
import org.example.healthcare_management_system.repository.DoctorSpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorSpecialityService {
    @Autowired
    private DoctorSpecialityRepository doctorSpecialityRepository;

    public DoctorSpeciality saveDoctorSpeciality(DoctorSpeciality doctorSpeciality) {
        return doctorSpecialityRepository.save(doctorSpeciality);
    }

    public List<DoctorSpeciality> getSpecialityByDoctorId(String doctorId) {
        return doctorSpecialityRepository.getSpecialityByDoctorId(doctorId);
    }

    public List<DoctorSpeciality> getAllDoctorSpecialities() {
        return doctorSpecialityRepository.findAll();
    }

    public Optional<DoctorSpeciality> getDoctorSpecialityById(UUID specialityId) {
        return doctorSpecialityRepository.findById(specialityId);
    }

    public DoctorSpeciality updateDoctorSpeciality(UUID specialityId, DoctorSpeciality doctorSpeciality ) {
        if (doctorSpecialityRepository.existsById(specialityId)) {
            doctorSpeciality.setSpecialityId(specialityId); // Ensure the provided user has the correct ID
            return doctorSpecialityRepository.save(doctorSpeciality);
        }
        return null; // Or throw an exception indicating the user was not found
    }

    public void deleteDoctorSpeciality(UUID specialityId) {
        doctorSpecialityRepository.deleteById(specialityId);
    }

}

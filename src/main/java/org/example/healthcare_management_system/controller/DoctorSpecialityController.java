package org.example.healthcare_management_system.controller;

import org.example.healthcare_management_system.model.DoctorDegree;
import org.example.healthcare_management_system.model.DoctorSpeciality;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.example.healthcare_management_system.service.DoctorDegreeService;
import org.example.healthcare_management_system.service.DoctorSpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/speciality")
public class DoctorSpecialityController {

    @Autowired
    private DoctorSpecialityService doctorSpecialityService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/saveDoctorSpeciality")
    public DoctorSpeciality createDoctorSpeciality(@RequestBody Map<String, Object> doctorSpecialityFormData) {
        DoctorSpeciality doctorSpeciality = new DoctorSpeciality();

        UUID uuid = UUID.randomUUID();
        doctorSpeciality.setSpecialityId(uuid);
        doctorSpeciality.setSpeciality((String) doctorSpecialityFormData.get("speciality"));
        doctorSpeciality.setFeesAmount(Integer.parseInt((String) doctorSpecialityFormData.get("feesAmount")));
        String doctorEmail = (String) doctorSpecialityFormData.get("doctorId");
        User userDoctor = customUserDetailsService.getUserDetailsById(doctorEmail);
        doctorSpeciality.setDoctorId(userDoctor);
        return doctorSpecialityService.saveDoctorSpeciality(doctorSpeciality);
    }

        @GetMapping
    public List<DoctorSpeciality> getAllDoctorSpeciality() {
        return doctorSpecialityService.getAllDoctorSpecialities();
    }

    @GetMapping("/getSpecialityByDoctorId")
    public List<DoctorSpeciality> getSpecialityByDoctorId(@RequestParam(name="doctorId") String doctorId) {
        return doctorSpecialityService.getSpecialityByDoctorId(doctorId);
    }

    @GetMapping("/{specialityId}")
    public DoctorSpeciality getDoctorSpecialityById(@PathVariable UUID specialityId) {
        Optional<DoctorSpeciality> doctorSpecialityOptional = doctorSpecialityService.getDoctorSpecialityById(specialityId);
        return doctorSpecialityOptional.orElse(null); // Or handle the case when user is not found
    }

    @PutMapping("/{specialityId}")
    public DoctorSpeciality updateDoctorSpeciality(@PathVariable UUID specialityId, @RequestBody DoctorSpeciality doctorSpeciality) {
        return doctorSpecialityService.updateDoctorSpeciality(specialityId, doctorSpeciality);
    }

    @DeleteMapping("/{specialityId}")
    public void deleteDoctorSpeciality(@PathVariable UUID specialityId) {
        doctorSpecialityService.deleteDoctorSpeciality(specialityId);
    }
}
package org.example.healthcare_management_system.controller;

import org.example.healthcare_management_system.model.DoctorDegree;
import org.example.healthcare_management_system.model.User;
import org.example.healthcare_management_system.service.CustomUserDetailsService;
import org.example.healthcare_management_system.service.DoctorDegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/degree")
public class DoctorDegreeController {

    @Autowired
    private DoctorDegreeService doctorDegreeService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/saveDoctorDegree")
    public ResponseEntity<DoctorDegree> createDoctorDegree(@RequestBody Map<String, Object> doctorDegreeFormData) {
        DoctorDegree doctorDegree = new DoctorDegree();

        UUID uuid = UUID.randomUUID();
        doctorDegree.setDegreeId(uuid);
        doctorDegree.setDegree((String)doctorDegreeFormData.get("degree"));
        doctorDegree.setYear(Integer.parseInt((String)doctorDegreeFormData.get("year")));
        doctorDegree.setInstitute((String)doctorDegreeFormData.get("institute"));
        String doctorEmail = (String)doctorDegreeFormData.get("doctorId");
        User userDoctor=customUserDetailsService.getUserDetailsById(doctorEmail);

        if(userDoctor==null){
            return ResponseEntity.notFound().build();
        }

        doctorDegree.setDoctorId(userDoctor);
         doctorDegreeService.saveDoctorDegree(doctorDegree);
        return ResponseEntity.ok(doctorDegree);

    }


    @GetMapping
    public List<DoctorDegree> getAllDoctorDegrees() {
        return doctorDegreeService.getAllDoctorDegrees();
    }

    @GetMapping("/getDegreeByDoctorId")
    public List<DoctorDegree> getDegreeByDoctorId(@RequestParam(name="doctorId") String doctorId) {
        return doctorDegreeService.getDegreeByDoctorId(doctorId);
    }

    @GetMapping("/{degreeId}")
    public DoctorDegree getDoctorDegreeById(@PathVariable UUID degreeId) {
        Optional<DoctorDegree> doctorDegreeOptional = doctorDegreeService.getDoctorDegreeById(degreeId);
        return doctorDegreeOptional.orElse(null); // Or handle the case when user is not found
    }

    @PutMapping("/{degreeId}")
    public DoctorDegree updateDoctorDegree(@PathVariable UUID degreeId, @RequestBody DoctorDegree doctorDegree) {
        return doctorDegreeService.updateDoctorDegree(degreeId, doctorDegree);
    }

    @DeleteMapping("/{degreeId}")
    public void deleteDoctorDegree(@PathVariable UUID degreeId) {
        doctorDegreeService.deleteDoctorDegree(degreeId);
    }
}
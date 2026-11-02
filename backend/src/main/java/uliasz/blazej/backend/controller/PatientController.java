package uliasz.blazej.backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uliasz.blazej.backend.database.entity.Patient;
import uliasz.blazej.backend.database.repository.PatientRepository;
import uliasz.blazej.backend.dto.PatientDto;
import uliasz.blazej.backend.service.PatientService;

import java.net.URI;

@RestController
public class PatientController {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    public PatientController(PatientRepository patientRepository, PatientService patientService){
        this.patientRepository = patientRepository;
        this.patientService = patientService;
    };

    @GetMapping("api/v1/patient/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Integer id){
        PatientDto patientDto = patientService.GetPatientDto(id);
        if (patientDto == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(patientDto);
        }
    }

    @PostMapping("api/v1/auth/register/patient")
    public ResponseEntity<PatientDto> addPatient(@RequestBody Patient patient){
        Patient savedPatient = patientService.register(patient);

        PatientDto patientDto = new PatientDto(savedPatient.getId(), savedPatient.getName(),
                savedPatient.getPhoneNumber(), savedPatient.getEmail(), savedPatient.getPESEL());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPatient.getId())
                .toUri();

        return ResponseEntity.created(location).body(patientDto);
    }

    @PutMapping("api/v1/patient/{id}")
    public ResponseEntity<PatientDto> putPatient(@PathVariable Integer id, @RequestBody Patient patient){
        return patientRepository.findById(id).map(existingPatient -> {
            existingPatient.setName(patient.getName());
            existingPatient.setEmail(patient.getEmail());
            existingPatient.setPhoneNumber(patient.getPhoneNumber());
            existingPatient.setPESEL(patient.getPESEL());
            return patientRepository.save(existingPatient);
        }).map(savedPatient -> new PatientDto(
                savedPatient.getId(), savedPatient.getName(),
                savedPatient.getPhoneNumber(), savedPatient.getEmail(),
                savedPatient.getPESEL()))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());

    }

    @PutMapping("api/v1/patient/password/{id}")
    public ResponseEntity<Object> putPatientPassword(@PathVariable Integer id, @RequestBody Patient patient){
        return patientService.changePassword(id,patient)
                .map(updatedPatient -> ResponseEntity.noContent().build())
                .orElseGet(()-> ResponseEntity.notFound().build());

    }

    @DeleteMapping("api/v1/patient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        patientRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

package uliasz.blazej.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uliasz.blazej.backend.database.entity.Patient;
import uliasz.blazej.backend.database.repository.PatientRepository;
import uliasz.blazej.backend.dto.PatientDto;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder){
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Patient register(Patient patient) {
        String hashedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(hashedPassword);

        patientRepository.save(patient);
        return patient;
    }

    public Optional<Patient> changePassword(Integer id, Patient patient) {
        return patientRepository.findById(id).map(existingPatient -> {
            existingPatient.setPassword(passwordEncoder.encode(patient.getPassword()));
            return patientRepository.save(existingPatient);
        });
    }

    public PatientDto GetPatientDto(Integer id){
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.map(value -> new PatientDto(value.getId(),
                value.getName(),
                value.getPhoneNumber(),
                value.getEmail(),
                value.getPESEL())).orElse(null);


    }
}

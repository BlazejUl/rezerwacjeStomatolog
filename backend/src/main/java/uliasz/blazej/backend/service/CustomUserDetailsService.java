package uliasz.blazej.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uliasz.blazej.backend.config.CustomUserDetails;
import uliasz.blazej.backend.database.repository.DentistRepository;
import uliasz.blazej.backend.database.repository.PatientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;

    public CustomUserDetailsService(PatientRepository patientRepository, DentistRepository dentistRepository){
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        var patient = patientRepository.findByEmail(email);
        if (patient.isPresent()) {
            return new CustomUserDetails(patient.get(), "ROLE_PATIENT");
        }

        var dentist = dentistRepository.findByEmail(email);
        if (dentist.isPresent()) {
            return new CustomUserDetails(dentist.get(), "ROLE_DENTIST");
        }

        throw new UsernameNotFoundException("Nie znaleziono użytkownika o emailu: " + email);
    }
}

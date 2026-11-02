package uliasz.blazej.backend.service;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uliasz.blazej.backend.database.entity.Dentist;
import uliasz.blazej.backend.database.repository.DentistRepository;
import uliasz.blazej.backend.dto.DentistDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DentistService {


    private final DentistRepository dentistRepository;
    private final PasswordEncoder passwordEncoder;

    public DentistService(DentistRepository dentistRepository, PasswordEncoder passwordEncoder){
        this.dentistRepository = dentistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Dentist register(Dentist dentist) {
        String hashedPassword = passwordEncoder.encode(dentist.getPassword());
        dentist.setPassword(hashedPassword);

        dentistRepository.save(dentist);
        return dentist;
    }

    public Optional<Dentist> changePassword(Integer id, Dentist dentist){
        return dentistRepository.findById(id).map(existingDentist ->{
            existingDentist.setPassword(passwordEncoder.encode(dentist.getPassword()));
            return dentistRepository.save(existingDentist);
        });
    }

    public List<DentistDto> getAllDentistsForPatient(){
        Iterable<Dentist> dentists = dentistRepository.findAll();
        List<DentistDto> dentistsDtos = new ArrayList<>();

        for (Dentist dentist:dentists){
            DentistDto dto = new DentistDto(dentist.getId(), dentist.getName(), dentist.getEmail());
            dentistsDtos.add(dto);
        }
        return dentistsDtos;
    }
}

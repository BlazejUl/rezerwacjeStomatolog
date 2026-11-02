package uliasz.blazej.backend.database.repository;

import org.springframework.data.repository.CrudRepository;
import uliasz.blazej.backend.database.entity.Patient;
import uliasz.blazej.backend.database.entity.PatientNote;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient,Integer> {
    Optional<Patient> findByEmail(String email);
}

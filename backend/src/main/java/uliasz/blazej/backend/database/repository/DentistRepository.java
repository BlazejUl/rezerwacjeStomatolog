package uliasz.blazej.backend.database.repository;

import org.springframework.data.repository.CrudRepository;
import uliasz.blazej.backend.database.entity.Dentist;
import uliasz.blazej.backend.database.entity.Patient;

import java.util.Optional;

public interface DentistRepository extends CrudRepository<Dentist,Integer> {
    Optional<Dentist> findByEmail(String email);
}

package uliasz.blazej.backend.database.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import uliasz.blazej.backend.database.entity.PatientNote;


import java.time.LocalDateTime;
import java.util.List;

public interface PatientNoteRepository extends CrudRepository<PatientNote,Integer> {
    List<PatientNote> findAllByIdPatient(Integer id_patient);

    @Procedure(procedureName = "add_note")
    Integer savePatientNote(Integer idPatient, String Note, LocalDateTime addDate , Integer idDentist);
}

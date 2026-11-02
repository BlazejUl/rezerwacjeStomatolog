package uliasz.blazej.backend.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uliasz.blazej.backend.database.entity.Dentist;

import java.time.LocalDateTime;

public record PatientNoteDto(
        Integer id,
        Integer idPatient,
        String patientNote,
        LocalDateTime addDate,
        DentistDto dentistDto
) {
}

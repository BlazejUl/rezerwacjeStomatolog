package uliasz.blazej.backend.service;

import org.springframework.stereotype.Service;
import uliasz.blazej.backend.database.entity.PatientNote;
import uliasz.blazej.backend.database.repository.PatientNoteRepository;
import uliasz.blazej.backend.dto.DentistDto;
import uliasz.blazej.backend.dto.PatientNoteDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientNoteService {

    private final PatientNoteRepository patientNoteRepository;

    PatientNoteService(PatientNoteRepository patientNoteRepository){
        this.patientNoteRepository = patientNoteRepository;
    }

    public List<PatientNoteDto> getAllPatientNotes(Integer patient_id){
        Iterable<PatientNote> patientNotes = patientNoteRepository.findAllByIdPatient(patient_id);
        List<PatientNoteDto> patientNoteDtos = new ArrayList<>();
        for (PatientNote patientNote:patientNotes){
            PatientNoteDto dto = new PatientNoteDto(
                    patientNote.getId(),patientNote.getIdPatient(),
                    patientNote.getPatientNote(),patientNote.getAddDate(),
                    new DentistDto(patientNote.getDentist().getId(),patientNote.getDentist().getName(),patientNote.getDentist().getEmail()));
            patientNoteDtos.add(dto);
        }
        return patientNoteDtos;
    }

}

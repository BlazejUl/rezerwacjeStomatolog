package uliasz.blazej.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uliasz.blazej.backend.database.entity.PatientNote;
import uliasz.blazej.backend.database.repository.PatientNoteRepository;
import uliasz.blazej.backend.dto.DentistDto;
import uliasz.blazej.backend.dto.PatientNoteDto;
import uliasz.blazej.backend.service.PatientNoteService;

import java.net.URI;
import java.util.List;

@RestController
public class PatientNoteController {


    private final PatientNoteService patientNoteService;
    private final PatientNoteRepository patientNoteRepository;

    PatientNoteController(PatientNoteService patientNoteService, PatientNoteRepository patientNoteRepository){
        this.patientNoteService = patientNoteService;
        this.patientNoteRepository = patientNoteRepository;
    }

    @GetMapping("api/v1/patient/notes/{idPatient}")
    public ResponseEntity<List<PatientNoteDto>> getAllPatientNotes(@PathVariable Integer idPatient){
        return ResponseEntity.ok(patientNoteService.getAllPatientNotes(idPatient));
    }

    @PostMapping("api/v1/patient/note")
    public ResponseEntity<PatientNoteDto> postPatientNote(@RequestBody PatientNote patientNote){
        Integer savedPatientNoteId = patientNoteRepository.savePatientNote(patientNote.getIdPatient(),
                patientNote.getPatientNote(),patientNote.getAddDate(),patientNote.getDentist().getId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPatientNoteId)
                .toUri();

        PatientNoteDto patientNoteDto = new PatientNoteDto(savedPatientNoteId,
                patientNote.getIdPatient(),patientNote.getPatientNote(),patientNote.getAddDate(),
                new DentistDto(patientNote.getDentist().getId(),patientNote.getDentist().getName(),patientNote.getDentist().getEmail()));

        return ResponseEntity.created(location).body(patientNoteDto);
    }

}

package uliasz.blazej.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uliasz.blazej.backend.database.entity.Dentist;
import uliasz.blazej.backend.database.repository.DentistRepository;
import uliasz.blazej.backend.dto.DentistDto;
import uliasz.blazej.backend.service.DentistService;

import java.net.URI;
import java.util.List;

@RestController
public class DentistController {

    private final DentistRepository dentistRepository;
    private final DentistService dentistService;

    DentistController(DentistRepository dentistRepository, DentistService dentistService){
        this.dentistRepository = dentistRepository;
        this.dentistService = dentistService;
    }


    @GetMapping("api/vi/dentists")
    public ResponseEntity<List<DentistDto>> getAllDentists(){
        return ResponseEntity.ok(dentistService.getAllDentistsForPatient());
    }

    @PostMapping("api/v1/auth/register/dentist")
    public ResponseEntity<DentistDto> addPatient(@RequestBody Dentist dentist){
        Dentist savedDentist = dentistService.register(dentist);

        DentistDto dentistDto = new DentistDto(savedDentist.getId(), savedDentist.getName(),
                savedDentist.getEmail());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDentist.getId())
                .toUri();

        return ResponseEntity.created(location).body(dentistDto);
    }

    @PutMapping("api/vi/dentist/{id}")
    public ResponseEntity<DentistDto> putDentist(@PathVariable Integer id, @RequestBody Dentist dentist){
        return dentistRepository.findById(id).map(existingDentist ->{
            existingDentist.setName(dentist.getName());
            existingDentist.setEmail(dentist.getEmail());
            return dentistRepository.save(existingDentist);
        }).map(savedDentist -> new DentistDto(
                savedDentist.getId(), savedDentist.getName(), savedDentist.getEmail()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("api/vi/dentist/password/{id}")
    public ResponseEntity<Object> putDentistPassword(@PathVariable Integer id, @RequestBody Dentist dentist){
        return dentistService.changePassword(id,dentist)
                .map(existingDentist -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

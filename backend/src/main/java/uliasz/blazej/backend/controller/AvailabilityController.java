package uliasz.blazej.backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uliasz.blazej.backend.database.entity.DentistAvailability;
import uliasz.blazej.backend.database.repository.DentistAvailabilityRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@RestController
public class AvailabilityController {


    private final DentistAvailabilityRepository dentistAvailabilityRepository;

    AvailabilityController(DentistAvailabilityRepository dentistAvailabilityRepository){
        this.dentistAvailabilityRepository = dentistAvailabilityRepository;
    }

    @GetMapping("api/v1/availabilities")
    public ResponseEntity<Iterable<DentistAvailability>> getAllAvailabilities(
            @RequestParam Integer idDentist,
            @RequestParam LocalDate date){
        Iterable<DentistAvailability> availabilities = dentistAvailabilityRepository
                .findByIdDentistAndDayGreaterThanEqual(idDentist, date);
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping("api/v1/availabilities/{id}")
    public ResponseEntity<DentistAvailability> getAvailability(@PathVariable Integer id){
        return dentistAvailabilityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("api/v1/availability")
    public ResponseEntity<DentistAvailability> postAvailability(@RequestBody DentistAvailability dentistAvailability){
        Integer newDentistAvailabilityId = dentistAvailabilityRepository.saveDentistAvailability(dentistAvailability.getIdDentist(),
                dentistAvailability.getDay(),dentistAvailability.getStart_time(),
                dentistAvailability.getEnd_time());
        dentistAvailability.setId(newDentistAvailabilityId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDentistAvailabilityId)
                .toUri();
        return ResponseEntity.created(location).body(dentistAvailability);
    }

    @PutMapping("api/v1/availability/{id}")
    public ResponseEntity<DentistAvailability> putAvailability(@PathVariable Integer id, @RequestBody DentistAvailability dentistAvailability){
        return dentistAvailabilityRepository.findById(id).map(existingAvailability -> {
            existingAvailability.setStart_time(dentistAvailability.getStart_time());
            existingAvailability.setEnd_time(dentistAvailability.getEnd_time());
            return dentistAvailabilityRepository.save(existingAvailability);
        })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("api/v1/availability/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Integer id){
        dentistAvailabilityRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

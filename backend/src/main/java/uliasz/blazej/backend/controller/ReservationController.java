package uliasz.blazej.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uliasz.blazej.backend.database.entity.Reservation;
import uliasz.blazej.backend.database.repository.ReservationRepository;
import uliasz.blazej.backend.service.ReservationService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    ReservationController(ReservationRepository reservationRepository, ReservationService reservationService){
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("api/v1/reservations/{idDentistAvailability}")
    public ResponseEntity<List<Reservation>> getAllReservationsFromDentistAvailability(@PathVariable Integer idDentistAvailability){
        return ResponseEntity.ok(reservationRepository.findAllByIdDentistAvailability(idDentistAvailability));
    }

    @GetMapping("api/v1/reservation/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id){
        return reservationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("api/v1/reservations")
    public ResponseEntity<Object> addReservationSafely(@RequestBody Reservation reservation){
        try {
            Integer id = reservationService.addReservationSafely(reservation);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(id)
                    .toUri();

            reservation.setId(id);
            return ResponseEntity.created(location).body(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("api/v1/reservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id){
        reservationRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

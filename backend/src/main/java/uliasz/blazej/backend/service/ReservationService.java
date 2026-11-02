package uliasz.blazej.backend.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uliasz.blazej.backend.database.entity.Reservation;
import uliasz.blazej.backend.database.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Integer addReservationSafely(Reservation reservation){
        return reservationRepository.saveReservation(reservation.getIdDentistAvailability(),
                reservation.getIdPatient(),reservation.getStart(),reservation.getEnd());

    }
}

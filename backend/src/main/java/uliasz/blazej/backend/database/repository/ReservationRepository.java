package uliasz.blazej.backend.database.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uliasz.blazej.backend.database.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation,Integer> {
    List<Reservation> findAllByIdDentistAvailability(Integer idDentistAvailability);

    @Procedure(procedureName = "add_reservation_safely", outputParameterName = "p_new_id")
    Integer saveReservation(
            @Param("p_id_dentist_availability") Integer idDentistAvailability,
            @Param("p_id_patient") Integer idPatient,
            @Param("p_start") LocalTime start,
            @Param("p_end") LocalTime end);
}

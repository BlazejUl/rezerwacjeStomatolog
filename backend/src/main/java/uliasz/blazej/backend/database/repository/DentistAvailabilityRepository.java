package uliasz.blazej.backend.database.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uliasz.blazej.backend.database.entity.DentistAvailability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DentistAvailabilityRepository extends CrudRepository<DentistAvailability,Integer> {
    List<DentistAvailability> findByIdDentistAndDayGreaterThanEqual(Integer idDentist, LocalDate day);

    @Procedure(procedureName = "add_availability", outputParameterName = "p_new_id")
    Integer saveDentistAvailability(
            @Param("p_id_dentist") Integer idDentist,
            @Param("p_day") LocalDate day,
            @Param("p_start_time") LocalTime start,
            @Param("p_end_time") LocalTime end);
}

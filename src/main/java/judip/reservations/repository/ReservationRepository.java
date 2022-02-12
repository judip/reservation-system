package judip.reservations.repository;

import judip.reservations.model.Reservation;
import judip.reservations.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    String FIND_BY_APARTMENT = "SELECT COUNT(r) > 0 FROM Reservation r WHERE r.apartment.id = :apartmentId AND\n" +
                               "(DATE_FROM BETWEEN :dateFrom AND :dateTo\n" +
                               "OR\n" +
                               "DATE_TO BETWEEN :dateFrom AND :dateTo\n" +
                               "OR\n" +
                               ":dateFrom BETWEEN DATE_FROM AND DATE_TO\n" +
                               "OR\n" +
                               ":dateTo BETWEEN DATE_FROM AND DATE_TO)";

    List<Reservation> findByApartmentId(Long id);

    List<Reservation> findByTenant(Tenant tenant);

    List<Reservation> findByTenantAndApartmentId(Tenant tenant, Long apartment);

    @Query(value= FIND_BY_APARTMENT)
    boolean bookingIsAvailable(Long apartmentId, LocalDate dateFrom, LocalDate dateTo);

    @Query(value= FIND_BY_APARTMENT + " AND id <> :id")
    boolean bookingIsAvailable(Long apartmentId, LocalDate dateFrom, LocalDate dateTo, Long id);

}

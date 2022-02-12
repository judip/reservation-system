package judip.reservations.services;

import judip.reservations.model.Apartment;
import judip.reservations.model.Reservation;
import judip.reservations.model.Tenant;
import judip.reservations.repository.ApartmentRepository;
import judip.reservations.repository.ReservationRepository;
import judip.reservations.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService {

    private final TenantRepository tenantRepository;
    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ReservationService(TenantRepository tenantRepository, ReservationRepository reservationRepository, ApartmentRepository apartmentRepository) {
        this.tenantRepository = tenantRepository;
        this.reservationRepository = reservationRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public Reservation createReservation(LocalDate dateFrom, LocalDate dateTo, Long apartmentId, String tenantName) {
        Tenant tenant = tenantRepository.findFirstByName(tenantName);
        if (tenant == null) {
            tenant = new Tenant();
            tenant.setName(tenantName);
            tenantRepository.save(tenant);
        }

        Apartment apartment = apartmentRepository.getById(apartmentId);
        BigDecimal cost = new BigDecimal(DAYS.between(dateFrom, dateTo)).multiply(apartment.getPrice());

        Reservation reservation = new Reservation();
        reservation.setDateFrom(dateFrom);
        reservation.setDateTo(dateTo);
        reservation.setApartment(apartment);
        reservation.setTenant(tenant);
        reservation.setCost(cost);
        return reservationRepository.save(reservation);
    }

    public Reservation changeReservation(Long id, LocalDate dateFrom, LocalDate dateTo, Long apartmentId, String tenantName) {
        Tenant tenant = tenantRepository.findFirstByName(tenantName);
        if (tenant == null) {
            tenant = new Tenant();
            tenant.setName(tenantName);
            tenantRepository.save(tenant);
        }

        Apartment apartment = apartmentRepository.getById(apartmentId);
        BigDecimal cost = new BigDecimal(DAYS.between(dateFrom, dateTo)).multiply(apartment.getPrice());

        Reservation reservation = reservationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        reservation.setDateFrom(dateFrom);
        reservation.setDateTo(dateTo);
        reservation.setApartment(apartment);
        reservation.setTenant(tenant);
        reservation.setCost(cost);
        return reservationRepository.save(reservation);
    }

    public boolean checkReservationDate(LocalDate dateFrom, LocalDate dateTo, Long apartmentId) {
        return reservationRepository.bookingIsAvailable(apartmentId, dateFrom, dateTo);
    }

    public boolean checkReservationDate(LocalDate dateFrom, LocalDate dateTo, Long apartmentId, Long id) {
        return reservationRepository.bookingIsAvailable(apartmentId, dateFrom, dateTo, id);
    }

    public List<Reservation> getReservationOrTenant(String tenantName, Long apartmentId) {
        Tenant tenant = tenantName == null ? null : tenantRepository.findFirstByName(tenantName);

        if (tenant != null && apartmentId != null) {
            return reservationRepository.findByTenantAndApartmentId(tenant, apartmentId);
        }

        if (tenant != null) {
            return reservationRepository.findByTenant(tenant);
        }

        if (apartmentId != null) {
            return reservationRepository.findByApartmentId(apartmentId);
        }

        return Collections.emptyList();
    }

}

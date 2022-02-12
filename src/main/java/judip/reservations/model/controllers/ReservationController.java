package judip.reservations.model.controllers;

import judip.reservations.dtos.ReservationDto;
import judip.reservations.model.Reservation;
import judip.reservations.requests.AddReservationRequest;
import judip.reservations.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody AddReservationRequest addReservationRequest) {
        if (reservationService.checkReservationDate(addReservationRequest.getDateFrom(), addReservationRequest.getDateTo(), addReservationRequest.getApartmentId(), null)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Reservation reservation = reservationService.createReservation(addReservationRequest.getDateFrom(), addReservationRequest.getDateTo(), addReservationRequest.getApartmentId(), addReservationRequest.getTenantName());
        return ResponseEntity.ok().body(reservation);
    }

    @PutMapping
    public ResponseEntity<Reservation> update(@RequestBody AddReservationRequest addReservationRequest) {

        if (reservationService.checkReservationDate(addReservationRequest.getDateFrom(), addReservationRequest.getDateTo(), addReservationRequest.getApartmentId(), addReservationRequest.getId())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Reservation reservation = reservationService.changeReservation(addReservationRequest.getId(), addReservationRequest.getDateFrom(), addReservationRequest.getDateTo(), addReservationRequest.getApartmentId(), addReservationRequest.getTenantName());
        return ResponseEntity.ok().body(reservation);
    }

    @GetMapping
    public List<ReservationDto> getReservationTenant(@RequestParam(required = false) String tenant, @RequestParam(required = false) Long apartment) {
        return reservationService.getReservationOrTenant(tenant, apartment).stream().map(ReservationController::mapped).collect(Collectors.toList());
    }

    private static ReservationDto mapped(Reservation reservation) {
        ReservationDto.ReservationDtoBuilder builder = ReservationDto.builder()
                .id(reservation.getId())
                .dateFrom(reservation.getDateFrom())
                .dateTo(reservation.getDateTo())
                .cost(reservation.getCost())
                .tenantName(reservation.getTenant().getName())
                .apartmentName(reservation.getApartment().getName());
        return builder.build();
    }

}

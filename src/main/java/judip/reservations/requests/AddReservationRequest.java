package judip.reservations.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddReservationRequest {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String tenantName;
    private Long apartmentId;
    private Long id;
}

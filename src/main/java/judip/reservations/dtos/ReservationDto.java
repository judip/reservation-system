package judip.reservations.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class ReservationDto {
    private Long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal cost;
    private String tenantName;
    private String apartmentName;
}

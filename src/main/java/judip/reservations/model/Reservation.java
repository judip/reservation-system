package judip.reservations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE")
    private LocalDate dateFrom;

    @Column(columnDefinition = "DATE")
    private LocalDate dateTo;

    private BigDecimal cost;

    @JsonIgnore
    @ManyToOne
    private Apartment apartment;

    @JsonIgnore
    @ManyToOne
    private Tenant tenant;
}

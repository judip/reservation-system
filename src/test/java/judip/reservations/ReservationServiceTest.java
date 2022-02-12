package judip.reservations;

import judip.reservations.model.Reservation;
import judip.reservations.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationServiceTest {

	@Autowired
	ReservationService reservationService;

	@Test
	void testCreateNewReservation() {
		// Given
		reservationService.createReservation(LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 5), 1L, "Jan Kowalski");

		// When
		List<Reservation> result = reservationService.getReservationOrTenant("Jan Kowalski", 1L);

		Reservation reservation = result.get(0);

		// Then
		assertEquals(LocalDate.of(2022, 10, 1), reservation.getDateFrom());
		assertEquals(LocalDate.of(2022, 10, 5), reservation.getDateTo());
		assertEquals(1L, reservation.getApartment().getId());
		assertEquals("Jan Kowalski", reservation.getTenant().getName());
	}

	@Test
	void testCheckReservationDateInAvailableTime() {
		// Given data.sql

		// When
		boolean result = reservationService.checkReservationDate(LocalDate.of(2022, 6, 16), LocalDate.of(2022, 6, 20), 1L);

		// Then
		assertFalse(result);
	}

	@Test
	void testCheckReservationDateInUnavailableTime() {
		// Given data.sql

		// When
		boolean result = reservationService.checkReservationDate(LocalDate.of(2022, 6, 15), LocalDate.of(2022, 6, 20), 1L);

		// Then
		assertTrue(result);
	}

}

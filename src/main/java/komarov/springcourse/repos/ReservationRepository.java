package komarov.springcourse.repos;

import komarov.springcourse.entities.orders.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

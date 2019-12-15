package komarov.springcourse.repos;

import komarov.springcourse.entities.orders.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationsByClient_Id(Long clientId);
}

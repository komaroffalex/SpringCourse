package komarov.springcourse.repos;

import komarov.springcourse.entities.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

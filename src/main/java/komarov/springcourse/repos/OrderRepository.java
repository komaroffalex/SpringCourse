package komarov.springcourse.repos;

import komarov.springcourse.entities.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByClient_Id(Long clientId);
}

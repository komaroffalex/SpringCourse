package komarov.springcourse.repos;

import komarov.springcourse.entities.orders.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}

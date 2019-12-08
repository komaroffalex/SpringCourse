package komarov.springcourse.repos;

import komarov.springcourse.entities.orders.Food;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodRepository extends CrudRepository<Food, Integer> {

}

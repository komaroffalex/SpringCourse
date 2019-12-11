package komarov.springcourse.repos;

import komarov.springcourse.entities.orders.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Long> {

}

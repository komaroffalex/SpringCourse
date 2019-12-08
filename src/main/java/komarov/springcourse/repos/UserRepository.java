package komarov.springcourse.repos;

import komarov.springcourse.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

}

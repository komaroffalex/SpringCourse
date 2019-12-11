package komarov.springcourse.repos;

import komarov.springcourse.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

}

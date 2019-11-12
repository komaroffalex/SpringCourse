package komarov.springcourse.repos;

import komarov.springcourse.entities.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository<T extends User> extends CrudRepository<T, Integer> {
    List<User> findByUserName(String name);
}

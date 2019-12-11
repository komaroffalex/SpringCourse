package komarov.springcourse.repos.users;

import komarov.springcourse.entities.users.Administrator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface AdministratorRepository extends UserRepository<Administrator> {

    @Query("select U from Administrator U where U.login = :login and type_user = 0")
    Optional<Administrator> getAdministratorByLogin(@Param("login") String login);
}

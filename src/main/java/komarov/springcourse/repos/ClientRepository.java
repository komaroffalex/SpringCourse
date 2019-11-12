package komarov.springcourse.repos;

import komarov.springcourse.entities.users.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ClientRepository extends UserRepository<Client> {

    @Query("select U from Client U where U.login = :login and TypeUser = 1")
    Optional<Client> getClientByLogin(@Param("login") String login);
}

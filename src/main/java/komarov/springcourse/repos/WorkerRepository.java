package komarov.springcourse.repos;

import komarov.springcourse.entities.users.Worker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface WorkerRepository extends UserRepository<Worker> {

    @Query("select U from Worker U where U.login = :login and TypeUser = 2")
    Optional<Worker> getWorkerByLogin(@Param("login") String login);
}

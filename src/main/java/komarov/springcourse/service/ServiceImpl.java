package komarov.springcourse.service;

import komarov.springcourse.entities.Role;
import komarov.springcourse.entities.users.Administrator;
import komarov.springcourse.entities.users.Client;
import komarov.springcourse.entities.users.Worker;
import komarov.springcourse.repos.AdministratorRepository;
import komarov.springcourse.repos.ClientRepository;
import komarov.springcourse.repos.FoodRepository;
import komarov.springcourse.repos.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServiceImpl {
    private FoodRepository foodRepository;
    private AdministratorRepository administratorRepository;
    private WorkerRepository workerRepository;
    private ClientRepository clientRepository;

    @Autowired
    public ServiceImpl(FoodRepository foodRepository, AdministratorRepository administratorRepository, WorkerRepository workerRepository,
                       ClientRepository clientRepository) {
        this.foodRepository = foodRepository;
        this.administratorRepository = administratorRepository;
        this.workerRepository = workerRepository;
        this.clientRepository = clientRepository;
    }

    public int authenticate(String login, String pwd) {
        if (loginClient(login, pwd)) {
            return 1;
        } else if (loginAdministrator(login, pwd)) {
            return 0;
        } else if (loginWorker(login, pwd)) {
            return 2;
        }
        return -1;
    }

    /**
     * Find user in the DB.
     *
     * @param userId specifies user's ID
     * @return user entity
     */
    public Administrator findUser(@NotNull final String userId) throws NoSuchElementException {
        return administratorRepository.findById(Long.parseLong(userId)).orElseThrow(()
                -> new NoSuchElementException("User is not present!"));
    }

    public List<Administrator> getAllAdministrators() throws NoSuchElementException {
        return administratorRepository.findAll();
    }

    public Long upsertUser(@NotNull final String login, @NotNull final String password,
                           @NotNull final String username) {
        final Administrator testUsr1 = new Administrator();
        testUsr1.setLogin(login);
        testUsr1.setPassword(password);
        testUsr1.setUserName(username);
        testUsr1.setTypeUser(Role.ADMINISTRATOR.getRoleId());
        return administratorRepository.save(testUsr1).getId();
    }

    public void deleteUser(@NotNull final String userId) throws NoSuchElementException {
        administratorRepository.findById(Long.parseLong(userId)).orElseThrow(()
                -> new NoSuchElementException("User is not present!"));
        administratorRepository.deleteById(Long.parseLong(userId));
    }

    public boolean loginClient(String login, String pwd) {
        Client client = clientRepository.getClientByLogin(login).orElse(null);
        if (client != null)
            return client.loginUser(pwd);
        return false;
    }

    public boolean loginWorker(String login, String pwd) {
        Worker worker = workerRepository.getWorkerByLogin(login).orElse(null);
        if (worker != null)
            return worker.loginUser(pwd);
        return false;
    }

    public boolean loginAdministrator(String login, String pwd) {
        Administrator admin = administratorRepository.getAdministratorByLogin(login).orElse(null);
        if (admin != null)
            return admin.loginUser(pwd);
        return false;
    }

    public void addNewClient(Client client){
        //TODO
        clientRepository.save(client);
    }

    public void addNewWorker(Worker worker){
        // TODO
        workerRepository.save(worker);
    }

    public void addNewOperator(Administrator admin){
        //TODO
        administratorRepository.save(admin);
    }
}

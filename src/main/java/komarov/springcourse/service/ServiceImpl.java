package komarov.springcourse.service;

import komarov.springcourse.entities.Role;
import komarov.springcourse.entities.users.Administrator;
import komarov.springcourse.entities.users.Client;
import komarov.springcourse.entities.users.User;
import komarov.springcourse.entities.users.Worker;
import komarov.springcourse.repos.*;
import komarov.springcourse.repos.users.AdministratorRepository;
import komarov.springcourse.repos.users.ClientRepository;
import komarov.springcourse.repos.users.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServiceImpl {
    private TableRepository tableRepository;
    private FoodRepository foodRepository;
    private OrderRepository orderRepository;
    private ReservationRepository reservationRepository;
    private AdministratorRepository administratorRepository;
    private WorkerRepository workerRepository;
    private ClientRepository clientRepository;

    @Autowired
    public ServiceImpl(FoodRepository foodRepository, AdministratorRepository administratorRepository,
                       WorkerRepository workerRepository, ClientRepository clientRepository,
                       TableRepository tableRepository, ReservationRepository reservationRepository,
                       OrderRepository orderRepository) {
        this.foodRepository = foodRepository;
        this.administratorRepository = administratorRepository;
        this.workerRepository = workerRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
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

    public List<Worker> getAllWorkers() throws NoSuchElementException {
        return workerRepository.findAll();
    }

    public List<Client> getAllClients() throws NoSuchElementException {
        return clientRepository.findAll();
    }

    public List<User> getAllUsers() throws NoSuchElementException {
        final List<User> users = new ArrayList<>();
        users.addAll(getAllAdministrators());
        users.addAll(getAllWorkers());
        users.addAll(getAllClients());
        return users;
    }

    public Long upsertUser(@NotNull final String login, @NotNull final String password,
                           @NotNull final String username, @NotNull final String role) throws NoSuchElementException {
        final User usr;
        switch(role){
            case "0":
                usr = new Administrator(login, password, username);
                return addNewAdministrator((Administrator)usr);
            case "1":
                usr = new Client(login, password, username);
                return addNewClient((Client)usr);
            case "2":
                usr = new Worker(login, password, username);
                return addNewWorker((Worker)usr);
            default:
                throw new NoSuchElementException("Incorrect Role!");
        }
    }

    public void deleteUser(@NotNull final String userId) throws NoSuchElementException {
        administratorRepository.findById(Long.parseLong(userId)).orElseThrow(()
                -> new NoSuchElementException("User is not present!"));
        administratorRepository.deleteById(Long.parseLong(userId));
    }

    private boolean loginClient(String login, String pwd) {
        Client client = clientRepository.getClientByLogin(login).orElse(null);
        if (client != null)
            return client.loginUser(pwd);
        return false;
    }

    private boolean loginWorker(String login, String pwd) {
        Worker worker = workerRepository.getWorkerByLogin(login).orElse(null);
        if (worker != null)
            return worker.loginUser(pwd);
        return false;
    }

    private boolean loginAdministrator(String login, String pwd) {
        Administrator admin = administratorRepository.getAdministratorByLogin(login).orElse(null);
        if (admin != null)
            return admin.loginUser(pwd);
        return false;
    }

    private Long addNewClient(Client client){
        return clientRepository.save(client).getId();
    }

    private Long addNewWorker(Worker worker){
        return workerRepository.save(worker).getId();
    }

    private Long addNewAdministrator(Administrator admin){
        return administratorRepository.save(admin).getId();
    }
}

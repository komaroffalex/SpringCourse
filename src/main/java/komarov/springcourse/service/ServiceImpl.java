package komarov.springcourse.service;

import komarov.springcourse.entities.users.Administrator;
import komarov.springcourse.entities.users.Client;
import komarov.springcourse.entities.users.Worker;
import komarov.springcourse.repos.AdministratorRepository;
import komarov.springcourse.repos.ClientRepository;
import komarov.springcourse.repos.FoodRepository;
import komarov.springcourse.repos.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

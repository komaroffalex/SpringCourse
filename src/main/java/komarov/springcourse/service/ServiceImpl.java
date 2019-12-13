package komarov.springcourse.service;

import komarov.springcourse.entities.Role;
import komarov.springcourse.entities.Status;
import komarov.springcourse.entities.orders.Food;
import komarov.springcourse.entities.orders.Order;
import komarov.springcourse.entities.orders.Reservation;
import komarov.springcourse.entities.orders.TableEntity;
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
import java.util.*;

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
    public User findUser(@NotNull final String userId) throws NoSuchElementException {
        if (administratorRepository.findById(Long.parseLong(userId)).isPresent()) {
            return administratorRepository.findById(Long.parseLong(userId)).get();
        } else if (clientRepository.findById(Long.parseLong(userId)).isPresent()) {
            return clientRepository.findById(Long.parseLong(userId)).get();
        } else if (workerRepository.findById(Long.parseLong(userId)).isPresent()) {
            return workerRepository.findById(Long.parseLong(userId)).get();
        } else {
            throw new NoSuchElementException("User not found!");
        }
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
        if (administratorRepository.findById(Long.parseLong(userId)).isPresent()) {
            administratorRepository.deleteById(Long.parseLong(userId));
        } else if (clientRepository.findById(Long.parseLong(userId)).isPresent()) {
            clientRepository.deleteById(Long.parseLong(userId));
        } else if (workerRepository.findById(Long.parseLong(userId)).isPresent()) {
            workerRepository.deleteById(Long.parseLong(userId));
        } else {
            throw new NoSuchElementException("User not found!");
        }
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

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order findOrder(@NotNull final String orderId) throws NoSuchElementException {
        return orderRepository.findById(Long.parseLong(orderId)).orElseThrow(()
                -> new NoSuchElementException("Order is not present!"));
    }

    public Long upsertOrder(@NotNull final String delTime, @NotNull final String foodIds,
                            @NotNull final String address, @NotNull final String clientId) throws NoSuchElementException {
        final Order order = new Order();
        List<String> foods = Arrays.asList(foodIds.split("\\s*,\\s*"));
        List<String> foodNamesList = new ArrayList<>();
        float totalCost = 0;
        for (String id : foods) {
            Optional<Food> item = foodRepository.findById(Long.parseLong(id));
            if (item.isPresent()) {
                foodNamesList.add(item.get().getName());
                totalCost = totalCost + item.get().getFoodCost();
            } else {
                throw new NoSuchElementException("Specified food id is not present!");
            }
        }
        String foodNamesStr = String.join(", ", foodNamesList);
        Optional<Client> client = clientRepository.findById(Long.parseLong(clientId));
        if (!client.isPresent()) {
            throw new NoSuchElementException("Client is not registered!");
        }
        if (workerRepository.findAll().size() == 0) throw new NoSuchElementException("Not enough workers!");
        Long leastBusyWorkerId = Order.getLeastBusyWorkerId(orderRepository.findAll(), workerRepository.findAll());
        order.setFood(foodNamesStr);
        order.setAddress(address);
        order.setClient(client.get());
        order.setDeliveryTime(delTime);
        order.setCost(totalCost);
        order.setWorker(workerRepository.findById(leastBusyWorkerId).get());
        order.setOrderStatus(Status.SUBMITTED);
        return orderRepository.save(order).getId();
    }

    public void deleteOrder(@NotNull final String orderId) throws NoSuchElementException {
        if (orderRepository.findById(Long.parseLong(orderId)).isPresent()) {
            orderRepository.deleteById(Long.parseLong(orderId));
        } else {
            throw new NoSuchElementException("Order not found!");
        }
    }

    public Long changeOrderStatus(@NotNull final String orderId, @NotNull final String newStatusId)
                                                                throws NoSuchElementException {
        Optional<Order> order = orderRepository.findById(Long.parseLong(orderId));
        if (order.isPresent()) {
            Order ord = order.get();
            ord.setOrderStatus(Status.valueOf(Integer.parseInt(newStatusId)));
            orderRepository.deleteById(Long.parseLong(orderId));
            return orderRepository.save(ord).getId();
        } else {
            throw new NoSuchElementException("Order not found!");
        }
    }

    public List<Food> getAllFood(){
        return foodRepository.findAll();
    }

    public Food findFood(@NotNull final String foodId) throws NoSuchElementException {
        return foodRepository.findById(Long.parseLong(foodId)).orElseThrow(()
                -> new NoSuchElementException("Food is not present!"));
    }

    public Long upsertFood(@NotNull final String foodname, @NotNull final String foodcost) {
        Food food = new Food();
        food.setFoodCost(Float.parseFloat(foodcost));
        food.setName(foodname);
        return foodRepository.save(food).getId();
    }

    public void deleteFood(@NotNull final String foodId) throws NoSuchElementException {
        if (foodRepository.findById(Long.parseLong(foodId)).isPresent()) {
            foodRepository.deleteById(Long.parseLong(foodId));
        } else {
            throw new NoSuchElementException("Food not found!");
        }
    }

    public List<TableEntity> getAllTables(){
        return tableRepository.findAll();
    }

    public TableEntity findTable(@NotNull final String tableId) throws NoSuchElementException {
        return tableRepository.findById(Long.parseLong(tableId)).orElseThrow(()
                -> new NoSuchElementException("Table is not present!"));
    }

    public Long upsertTable(@NotNull final String seats, @NotNull final String location) {
        TableEntity tableEntity = new TableEntity();
        tableEntity.setSeats(Integer.parseInt(seats));
        tableEntity.setLocation(location);
        tableEntity.setIsOccupied(0);
        return tableRepository.save(tableEntity).getId();
    }

    public void deleteTable(@NotNull final String tableId) throws NoSuchElementException {
        if (tableRepository.findById(Long.parseLong(tableId)).isPresent()) {
            tableRepository.deleteById(Long.parseLong(tableId));
        } else {
            throw new NoSuchElementException("Table not found!");
        }
    }

    public Long changeTableStatus(@NotNull final String tableId, @NotNull final String isOccupied)
            throws NoSuchElementException {
        Optional<TableEntity> tableOpt = tableRepository.findById(Long.parseLong(tableId));
        if (tableOpt.isPresent()) {
            TableEntity table = tableOpt.get();
            table.setIsOccupied(Integer.parseInt(isOccupied));
            tableRepository.deleteById(Long.parseLong(tableId));
            return tableRepository.save(table).getId();
        } else {
            throw new NoSuchElementException("Table not found!");
        }
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Reservation findReservation(@NotNull final String reservationId) throws NoSuchElementException {
        return reservationRepository.findById(Long.parseLong(reservationId)).orElseThrow(()
                -> new NoSuchElementException("Reservation is not present!"));
    }

    public Long upsertReservation(@NotNull final String reservationTime, @NotNull final String persons,
                                  @NotNull final String tableId, @NotNull final String clientId)
            throws NoSuchElementException {
        Reservation reservation = new Reservation();
        reservation.setPersons(Integer.parseInt(persons));
        reservation.setReservationTime(reservationTime);
        Optional<TableEntity> tableOpt = tableRepository.findById(Long.parseLong(tableId));
        Optional<Client> clientOpt = clientRepository.findById(Long.parseLong(clientId));
        if(!tableOpt.isPresent() || !clientOpt.isPresent()) {
            throw new NoSuchElementException("Specified table or client do not exist!");
        }
        reservation.setStatus(Status.SUBMITTED);
        reservation.setTable(tableOpt.get());
        reservation.setClient(clientOpt.get());
        reservation.setCost(tableOpt.get().getCost());
        return reservationRepository.save(reservation).getId();
    }

    public void deleteReservation(@NotNull final String reservationId) throws NoSuchElementException {
        if (reservationRepository.findById(Long.parseLong(reservationId)).isPresent()) {
            reservationRepository.deleteById(Long.parseLong(reservationId));
        } else {
            throw new NoSuchElementException("Reservation not found!");
        }
    }

    public Long changeReservationStatus(@NotNull final String reservationId, @NotNull final String newStatusId)
            throws NoSuchElementException {
        Optional<Reservation> reservationOpt = reservationRepository.findById(Long.parseLong(reservationId));
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            reservation.setStatus(Status.valueOf(Integer.parseInt(newStatusId)));
            reservationRepository.deleteById(Long.parseLong(reservationId));
            return reservationRepository.save(reservation).getId();
        } else {
            throw new NoSuchElementException("Reservation not found!");
        }
    }
}

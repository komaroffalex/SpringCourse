package komarov.springcourse.entities.orders;

import komarov.springcourse.entities.Status;
import komarov.springcourse.entities.users.Client;
import komarov.springcourse.entities.users.Worker;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "delTime")
    private String deliveryTime;
    private String food;
    private float cost;
    private String address;
    private Status orderStatus;
    @ManyToOne
    @JoinColumn(name = "workerId")
    private Worker worker;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    private String getFoodAsString(List<Food> food){
        List<String> foodNames = new ArrayList<>();
        for(Food it : food){
            foodNames.add(it.getName());
        }
        return String.join(",", foodNames);
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setFood(List<Food> food) {this.food = getFoodAsString(food);}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Long getLeastBusyWorkerId(final List<Order> orders, final List<Worker> workers) {
        if (orders.size() == 0) return workers.get(0).getId();
        Map<Long, Integer> ordersWorkersMap = new HashMap<>();
        for (Order ord : orders) {
            Long workerId = ord.worker.getId();
            if (ordersWorkersMap.containsKey(workerId)) {
                ordersWorkersMap.put(workerId, ordersWorkersMap.get(workerId) + 1);
            } else {
                ordersWorkersMap.put(workerId, 0);
            }
        }
        for (Worker it : workers) {
            if (!ordersWorkersMap.containsKey(it.getId())) return it.getId();
        }
        Long leastBusyWorkerId = -1L;
        Integer minimalOrdersTaken = 1000;
        for (Long key : ordersWorkersMap.keySet()) {
            if (ordersWorkersMap.get(key) < minimalOrdersTaken) {
                minimalOrdersTaken = ordersWorkersMap.get(key);
                leastBusyWorkerId = key;
            }
        }
        return leastBusyWorkerId;
    }
}

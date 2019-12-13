package komarov.springcourse.controllers;

import komarov.springcourse.entities.orders.Food;
import komarov.springcourse.entities.orders.Order;
import komarov.springcourse.entities.orders.Reservation;
import komarov.springcourse.entities.orders.TableEntity;
import komarov.springcourse.entities.users.Administrator;
import komarov.springcourse.entities.users.User;
import komarov.springcourse.repos.ReservationRepository;
import komarov.springcourse.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
public class AdministratorController {

    private ServiceImpl service;

    @Autowired
    public void setService(@NotNull final ServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/user/page")
    public String adminPage() {
        return "administrator.html";
    }

    /**
     * Get all users.
     *
     * @return list of all users
     */
    @RequestMapping(value = "/user/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get user by the specified ID.
     *
     * @param id user's ID
     * @return user's info
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> getUserById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(service.findUser(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete user by the specified ID.
     *
     * @param id user's ID
     */
    @RequestMapping(value = "/user", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeUserById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            service.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> upsertNewUser(@RequestParam String login, @RequestParam String password,
                                                @RequestParam String username, @RequestParam String role) {
        if (null == login || null == password || null == username || null == role) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(login) || "".equals(password) || "".equals(username) || "".equals(role)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final Long newId = service.upsertUser(login, password, username, role);
            final String resp = "{\"id\":" + newId.toString() + "}";
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all orders.
     *
     * @return list of all orders
     */
    @RequestMapping(value = "/order/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            return new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> upsertNewOrder(   @RequestParam String deltime, @RequestParam String foodids,
                                                    @RequestParam String address, @RequestParam String clientid) {
        if (null == deltime || null == foodids || null == address || null == clientid) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(deltime) || "".equals(foodids) || "".equals(address) || "".equals(clientid)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final Long newId = service.upsertOrder(deltime, foodids, address, clientid);
            final String resp = "{\"id\":" + newId.toString() + "}";
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/order/status", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> upsertNewOrderStatus(@RequestParam String orderid, @RequestParam String newstatus) {
        if (null == orderid || null == newstatus) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(orderid) || "".equals(newstatus)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final Long newId = service.changeOrderStatus(orderid, newstatus);
            final String resp = "{\"id\":" + newId.toString() + "}";
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeOrderById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            service.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Order> getOrderById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(service.findOrder(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/food/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Food>> getAllFood() {
        try {
            return new ResponseEntity<>(service.getAllFood(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/food", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> upsertNewFood(   @RequestParam String foodname, @RequestParam String foodcost) {
        if (null == foodname || null == foodcost) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(foodname) || "".equals(foodcost)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final Long newId = service.upsertFood(foodname, foodcost);
            final String resp = "{\"id\":" + newId.toString() + "}";
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/food", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeFoodById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            service.deleteFood(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/food", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Food> getFoodById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(service.findFood(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/table/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<TableEntity>> getAllTables() {
        try {
            return new ResponseEntity<>(service.getAllTables(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/table", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> upsertNewTable(   @RequestParam String seats, @RequestParam String location) {
        if (null == seats || null == location) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(seats) || "".equals(location)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final Long newId = service.upsertTable(seats, location);
            final String resp = "{\"id\":" + newId.toString() + "}";
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/table", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeTableById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            service.deleteTable(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/table", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TableEntity> getTableById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(service.findTable(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reservation/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            return new ResponseEntity<>(service.getAllReservations(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> upsertNewReservation( @RequestParam String reservationtime,
                                                        @RequestParam String persons,
                                                        @RequestParam String tableid, @RequestParam String clientid) {
        if (null == reservationtime || null == persons || null == tableid || null == clientid) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(reservationtime) || "".equals(persons) || "".equals(tableid) || "".equals(clientid)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final Long newId = service.upsertReservation(reservationtime, persons, tableid, clientid);
            final String resp = "{\"id\":" + newId.toString() + "}";
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reservation/status", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> upsertNewReservationStatus( @RequestParam String reservationid,
                                                              @RequestParam String newstatus) {
        if (null == reservationid || null == newstatus) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ("".equals(reservationid) || "".equals(newstatus)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final Long newId = service.changeReservationStatus(reservationid, newstatus);
            final String resp = "{\"id\":" + newId.toString() + "}";
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeReservationById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            service.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Reservation> getReservationById(@RequestParam String id) {
        if (null == id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(service.findReservation(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package komarov.springcourse.controllers;

import komarov.springcourse.entities.orders.Food;
import komarov.springcourse.entities.orders.Order;
import komarov.springcourse.entities.orders.Reservation;
import komarov.springcourse.entities.orders.TableEntity;
import komarov.springcourse.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private ServiceImpl service;

    @Autowired
    public void setService(@NotNull final ServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/user/page")
    public String workerPage() {
        return "worker.html";
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


    /**
     * Get all orders.
     *
     * @return list of all orders
     */
    @RequestMapping(value = "/order/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Order>> getAllWorkerOrders(@RequestParam String id) {
        try {
            return new ResponseEntity<>(service.getAllWorkerOrders(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

}

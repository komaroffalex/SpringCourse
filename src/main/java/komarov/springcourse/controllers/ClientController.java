package komarov.springcourse.controllers;

import komarov.springcourse.entities.orders.Order;
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
@RequestMapping("/client")
public class ClientController {

    private ServiceImpl service;

    @Autowired
    public void setService(@NotNull final ServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/user/page")
    public String adminPage() {
        return "client.html";
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
    public ResponseEntity<String> upsertNewOrder(@RequestParam String deltime, @RequestParam String foodids,
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
}

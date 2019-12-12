package komarov.springcourse.controllers;

import komarov.springcourse.entities.users.Administrator;
import komarov.springcourse.entities.users.User;
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
    public ResponseEntity<Administrator> getUserById(@RequestParam String id) {
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
}

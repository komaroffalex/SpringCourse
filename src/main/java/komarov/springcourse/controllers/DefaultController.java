package komarov.springcourse.controllers;

import komarov.springcourse.entities.users.Administrator;
import komarov.springcourse.entities.users.User;
import komarov.springcourse.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

@RestController
@RequestMapping
public class DefaultController{
    private ServiceImpl service;

    @Autowired
    public void setService(@NotNull final ServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String list() {
        return "index";
    }

    /**
     * Get user by the specified ID.
     *
     * @param login user's login
     * @param password user's password
     * @return user's role id
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Integer> logInUser(@RequestParam String login, @RequestParam String password) {
        if (null == login) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            final int userRole = service.authenticate(login, password);
            return userRole == -1 ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(userRole, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

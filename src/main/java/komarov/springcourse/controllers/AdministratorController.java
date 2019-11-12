package komarov.springcourse.controllers;

import komarov.springcourse.entities.users.Administrator;
import komarov.springcourse.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdministratorController {
    @Autowired
    ServiceImpl service;

    @RequestMapping("rest/administrator/{login}/authenticate")
    public String authenticate(@PathVariable String login, @RequestParam("passwd") String passwd) {
        if (service.authenticate(login, passwd) != 0)
            return "false";
        return "true";
    }

    @RequestMapping(value = "rest/administrator/{login}", method = RequestMethod.POST)
    public void newOperator(@PathVariable String login, @RequestBody Administrator admin) {
        service.addNewOperator(admin);
    }

    /*@RequestMapping(value = "rest/operator/{login}", method = RequestMethod.GET)
    public Operator getOperator(@PathVariable String login) {
        service.findOperatorByLogin(login).orElseThrow(() -> new.. .//???
    }*/
}

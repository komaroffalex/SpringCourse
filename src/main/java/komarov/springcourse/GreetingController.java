package komarov.springcourse;

import komarov.springcourse.entities.orders.Food;
import komarov.springcourse.entities.users.User;
import komarov.springcourse.repos.FoodRepository;
import komarov.springcourse.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/food")
    public String main(Map<String, Object> model) {
        Iterable<Food> foods = foodRepository.findAll();

        model.put("foods", foods);
        return "main";
    }

    @PostMapping("/food")
    public String add(@RequestParam String foodName, @RequestParam String foodCost, Map<String, Object> model) {
        Food food = new Food(foodName, foodCost);

        foodRepository.save(food);

        Iterable<Food> foods = foodRepository.findAll();

        model.put("foods", foods);

        return "main";
    }

    @PostMapping("/food/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Food> foods;

        if (filter != null && !filter.isEmpty()) {
            foods = foodRepository.findByFoodName(filter);
        } else {
            foods = foodRepository.findAll();
        }

        model.put("foods", foods);

        return "main";
    }

    @GetMapping("/user")
    public String userMain(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();

        model.put("users", users);
        return "user";
    }

    @PostMapping("/user")
    public String userAdd(@RequestParam String login, @RequestParam String password, @RequestParam String userName,
                          @RequestParam String role, Map<String, Object> model) {
        User user = new User(login, password, userName, role);

        userRepository.save(user);

        Iterable<User> users = userRepository.findAll();

        model.put("users", users);

        return "user";
    }

    @PostMapping("/user/filter")
    public String userFilter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<User> users;

        if (filter != null && !filter.isEmpty()) {
            users = userRepository.findByUserName(filter);
        } else {
            users = userRepository.findAll();
        }

        model.put("users", users);

        return "user";
    }
}

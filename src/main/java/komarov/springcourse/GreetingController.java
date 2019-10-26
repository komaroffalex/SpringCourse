package komarov.springcourse;

import komarov.springcourse.entities.orders.Food;
import komarov.springcourse.repos.FoodRepository;
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

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Food> foods = foodRepository.findAll();

        model.put("foods", foods);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String foodName, Map<String, Object> model) {
        Food food = new Food(foodName);

        foodRepository.save(food);

        Iterable<Food> foods = foodRepository.findAll();

        model.put("foods", foods);

        return "main";
    }

    @PostMapping("filter")
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
}

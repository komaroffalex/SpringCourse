package komarov.springcourse.entities.orders;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Food {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String foodName;
    private float foodCost;

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Food otherFood = (Food) obj;
        return id == otherFood.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return foodName;
    }

    public void setName(String name) {
        this.foodName = name;
    }

    public float getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(float foodCost) {
        this.foodCost = foodCost;
    }
}

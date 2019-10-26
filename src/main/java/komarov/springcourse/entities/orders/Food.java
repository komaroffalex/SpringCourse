package komarov.springcourse.entities.orders;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Food {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String foodName;

    public Food() {

    }

    public Food(Integer id, String name) {
        this.id = id;
        this.foodName = name;
    }

    public Food(String name) {
        this.foodName = name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Food otherFood = (Food) obj;
        return id == otherFood.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return foodName;
    }

    public void setName(String name) {
        this.foodName = name;
    }
}

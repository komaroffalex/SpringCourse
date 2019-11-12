package komarov.springcourse.entities.users;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
@Data
public class Worker extends User {

    public Worker(){

    }

    public Worker(int id, String login, String password, String userName, int typeUser) {
        super(id, login, password, userName, typeUser);
    }
}

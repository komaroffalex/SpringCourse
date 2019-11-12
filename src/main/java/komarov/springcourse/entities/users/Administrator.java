package komarov.springcourse.entities.users;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0")
@Data
public class Administrator extends User {

    public Administrator(){

    }

    public Administrator(int id, String login, String password, String userName, int typeUser) {
        super(id, login, password, userName, typeUser);
    }
}

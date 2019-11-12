package komarov.springcourse.entities.users;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
@Data
public class Client extends User {

    public Client(){

    }

    public Client(int id, String login, String password, String userName, int typeUser) {
        super(id, login, password, userName, typeUser);
    }
}

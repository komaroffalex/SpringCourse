package komarov.springcourse.entities.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class Client extends User {
    public Client() {}

    public Client(final String login, final String password, final String username) {
        super(login, password, username, 1);
    }
}

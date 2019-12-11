package komarov.springcourse.entities.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Worker extends User {
    public Worker() {}

    public Worker(final String login, final String password, final String username) {
        super(login, password, username, 2);
    }
}

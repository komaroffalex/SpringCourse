package komarov.springcourse.entities.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class Administrator extends User {
    public Administrator() {}

    public Administrator(final String login, final String password, final String username) {
        super(login, password, username, 0);
    }
}

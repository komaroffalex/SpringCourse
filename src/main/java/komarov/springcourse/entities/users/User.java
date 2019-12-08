package komarov.springcourse.entities.users;

import komarov.springcourse.entities.Role;
import komarov.springcourse.entities.Status;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user")
@DiscriminatorColumn(name="typeUser",
        discriminatorType = DiscriminatorType.INTEGER)
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    private String userName;
    @Column(name="typeUser",insertable=false, updatable=false)
    private int typeUser;

    public int getTypeUser() {
        return this.typeUser;
    }

    public void seTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean loginUser(String pass) {
        if (this != null) {
            String originpwd = this.getPassword();
            if (originpwd.equals(pass)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public Status getTypeUserClass() {
        return Status.valueOf(this.typeUser);
    }
}

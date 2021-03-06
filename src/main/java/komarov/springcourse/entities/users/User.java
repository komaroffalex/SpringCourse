package komarov.springcourse.entities.users;

import komarov.springcourse.entities.Role;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user")
@DiscriminatorColumn(name="type_user",
        discriminatorType = DiscriminatorType.INTEGER)
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    private String userName;
    @Column(name="type_user",insertable=false, updatable=false)
    private int typeUser;

    public User() {

    }

    public User(final String login, final String password, final String userName, final int typeUser) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.typeUser = typeUser;
    }

    public int getTypeUser() {
        return this.typeUser;
    }

    public void setTypeUser(int typeUser) {
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

    public Role getTypeUserClass() {
        return Role.valueOf(this.typeUser);
    }
}

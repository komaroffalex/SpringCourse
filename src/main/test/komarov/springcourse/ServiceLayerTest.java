package komarov.springcourse;

import komarov.springcourse.entities.Role;
import komarov.springcourse.entities.users.User;
import komarov.springcourse.service.ServiceImpl;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    ServiceImpl facade;

    @Test
    public void getUserTest() {
        User testUser = new User();
        testUser.setLogin("testuser");
        testUser.setPassword("1111");
        testUser.setUserName("testuser");
        testUser.setTypeUser(0);
        Long userId = facade.upsertUser(testUser.getLogin(), testUser.getPassword(),
                testUser.getUserName(), String.valueOf(testUser.getTypeUser()));
        User getUser = facade.findUser(userId.toString());
        assertThat(getUser.getLogin()).isEqualTo(testUser.getLogin());
        assertThat(getUser.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(getUser.getUserName()).isEqualTo(testUser.getUserName());
        assertThat(getUser.getTypeUser()).isEqualTo(testUser.getTypeUser());
        facade.deleteUser(getUser.getId().toString());
    }

    @Test
    public void logInUserTest() {
        User testUser = new User();
        testUser.setLogin("testuser");
        testUser.setPassword("1111");
        testUser.setUserName("testuser");
        testUser.setTypeUser(0);
        Long userId = facade.upsertUser(testUser.getLogin(), testUser.getPassword(),
                testUser.getUserName(), String.valueOf(testUser.getTypeUser()));
        assertThat(facade.authenticate("testuser", "1111")).isEqualTo(0);
        assertThat(facade.getCurrentlyLoggedInUser().getLogin()).isEqualTo("testuser");
        facade.deleteUser(userId.toString());
    }

    @Test
    public void removeUserTest() {
        User testUser = new User();
        testUser.setLogin("testuser");
        testUser.setPassword("1111");
        testUser.setUserName("testuser");
        testUser.setTypeUser(0);
        Long userId = facade.upsertUser(testUser.getLogin(), testUser.getPassword(),
                testUser.getUserName(), String.valueOf(testUser.getTypeUser()));
        facade.deleteUser(userId.toString());
    }
}

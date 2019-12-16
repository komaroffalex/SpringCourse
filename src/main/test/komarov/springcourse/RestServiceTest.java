package komarov.springcourse;

import komarov.springcourse.entities.users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final static String BASEURL = "http://localhost:";

    @Test
    public void testAddAndGetUser() throws Exception {
        URI upsertUserUrl = new URI(BASEURL + port + "/" +
                "admin/user?login=testuser&password=1111&username=testuser&role=0");
        RequestEntity<?> reqEntity = RequestEntity.put(upsertUserUrl).build();
        ResponseEntity<String> respEntity = this.restTemplate.exchange(reqEntity, String.class);
        assertThat(respEntity.getStatusCode().toString()).contains("201");

        URI getUserUrl = new URI(BASEURL + port + "/" +
                "login?login=testuser&password=1111");
        RequestEntity<?> reqEntity2 = RequestEntity.get(getUserUrl).build();
        ResponseEntity<String> respEntity2 = this.restTemplate.exchange(reqEntity2, String.class);
        assertThat(respEntity2.getStatusCode().toString()).contains("200");

        URI getLogInUserUrl = new URI(BASEURL + port + "/" +
                "login/current");
        RequestEntity<?> reqEntity3 = RequestEntity.get(getLogInUserUrl).build();
        ResponseEntity<User> respEntity3 = this.restTemplate.exchange(reqEntity3, User.class);
        assertThat(respEntity3.getBody().getLogin()).contains("testuser");

        URI deleteUserUrl = new URI(BASEURL + port + "/" +
                "admin/user?id=" + respEntity3.getBody().getId());
        RequestEntity<?> reqEntity4 = RequestEntity.delete(deleteUserUrl).build();
        ResponseEntity<String> respEntity4 = this.restTemplate.exchange(reqEntity4, String.class);
        assertThat(respEntity4.getStatusCode().toString()).contains("202");
    }
}

package me.shawn.study.springh2example;

import me.shawn.study.springh2example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJdbcTest
public class UserRepositoryTest {

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        UserRepository repo = new UserRepository();
        User user = new User();
        user.setId("shawn");
        user.setName("신세훈");
        user.setPassword("1234");

        repo.add(user);

        User userFromRepository = repo.get("shawn");
        assertEquals(user.getName(), userFromRepository.getName());
        assertEquals(user.getId(), userFromRepository.getId());
        assertEquals(user.getPassword(), userFromRepository.getPassword());

    }
}
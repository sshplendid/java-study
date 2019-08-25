package me.shawn.study.springh2example;


import lombok.extern.slf4j.Slf4j;
import me.shawn.study.springh2example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.sql.*;

@Slf4j
public class UserRepository {
    private final Connection connection;
    private final String JDBC_URL = "jdbc:h2:mem:testdb";
    private final String USERNAME = "sa";
    private final String PASSWORD = "";
    public static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public boolean add(User user) throws SQLException {
        String sql = "INSERT INTO USER (id, name, password) values (?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        boolean executed = ps.execute();

        ps.close();

        return executed;
    }

    public User get(String id) throws SQLException {
        String sql = "SELECT * FROM USER where id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));

            return user;
        }
        return new User();
    }

    @PreDestroy
    public void tearDown() throws SQLException {
        if(connection != null && !connection.isClosed()) {
            log.debug("UserRepository Connection is closing...");
            connection.close();
        }
    }
}

package by.verbitsky.servletdemo.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserFactory <User> {
    Optional<User> createUser(ResultSet resultSet) throws SQLException;

    List<User> createUserList(ResultSet resultSet) throws SQLException;
}

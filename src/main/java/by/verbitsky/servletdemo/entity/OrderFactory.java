package by.verbitsky.servletdemo.entity;

import by.verbitsky.servletdemo.entity.ext.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderFactory <Order> {


    Optional<Order> createOrder (ResultSet resultSet) throws SQLException;



    List<Order> createOrders (ResultSet resultSet) throws SQLException;

    Order createOrder (User user, Set<Song> songs);
}

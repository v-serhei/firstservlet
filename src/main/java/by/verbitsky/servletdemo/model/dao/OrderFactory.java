package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface Order factory.
 * Factory provides construction of Order object or List of Order objects
 * <p>
 *
 * @param <Order> the type parameter
 *                <p>
 * @author Verbitsky Sergey
 * @version 1.0
 * @see OrderDao
 */
public interface OrderFactory<Order> {
    /**
     * Create simple order without order description (description contains list of order elements)
     * Method doesn't turn ResultSet iterator to next record
     *
     * @param resultSet contains record with order info
     * @return Optional value  Optional.of(Order) as result.
     * @throws SQLException if impossible to get values from ResultSet
     */
    Optional<Order> createSimpleOrder(ResultSet resultSet) throws SQLException;

    /**
     * Create List of Order objects without order description (description contains list of order elements)
     * Method used while and call next method of ResultSet object to get all records
     *
     * @param resultSet contains records with order info
     * @return the List of Order objects. If ResultSet doesn't contain records - it returns empty list
     * @throws SQLException if impossible to get values from ResultSet
     */
    List<Order> createSimpleOrders(ResultSet resultSet) throws SQLException;

    /**
     * Create an order with description (description contains list of order elements)
     * Method doesn't turn ResultSet iterator to next records
     *
     * @param resultSet contains record with order info
     * @return Optional value  Optional.of(Order) as result.
     * @throws SQLException if impossible to get values from ResultSet
     */
    Optional<Order> createOrder(ResultSet resultSet) throws SQLException;

    /**
     * Create Order object
     *
     * @param user  needed to set order field values
     * @param songs contains set of order description
     * @return the Order object as result
     */
    Order createOrder(User user, Set<Song> songs);
}

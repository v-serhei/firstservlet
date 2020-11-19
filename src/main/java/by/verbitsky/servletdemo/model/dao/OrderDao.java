package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.exception.DaoException;

import java.util.List;

/**
 * The interface Order dao extends {@link BaseDao} to provide C.R.U.D. methods for Order objects in application data base
 * Order dao interface also provides additional methods to process user commands
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see by.verbitsky.servletdemo.controller.command.Command
 * @see Order
 */

public interface OrderDao extends BaseDao<Order> {
    List<Order> findOrdersByUserId(long userId) throws DaoException;


    /**
     * Create order description in data base.
     *
     * @param order contains list of order items which needed to save to data base
     * @return the boolean true if operation successful and false if it isn't
     * @throws DaoException the dao exception if received Sql exception while processing query
     */
    boolean createOrderDescription(Order order) throws DaoException;

    /**
     * Remove order description from data base.
     *
     * @param orderId used in Sql query to find order
     * @param songId  used in Sql query to find each order description
     * @return the boolean true if operation successful and false if it isn't
     * @throws DaoException the dao exception if received Sql exception while processing query
     */
    boolean removeOrderDescription(long orderId, long songId) throws DaoException;
}

package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.exception.DaoException;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    List<Order> findOrdersByUserId (long userId) throws DaoException;

    boolean createOrderDescription(Order order) throws DaoException;

    boolean removeOrderDescription(long orderId, long songId) throws DaoException;
}

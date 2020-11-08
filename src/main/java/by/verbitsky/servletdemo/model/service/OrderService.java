package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    boolean addOrder (Order order) throws ServiceException;

    Optional<Order> findOrderById (long id) throws ServiceException;

    List<Order> findUserOrders (long userId) throws ServiceException;

    boolean removeSongFromOrder(long orderId, long songId) throws ServiceException;

    boolean deleteOrder(User user, long orderId) throws ServiceException;

    boolean updateOrder (Order order) throws ServiceException;
}

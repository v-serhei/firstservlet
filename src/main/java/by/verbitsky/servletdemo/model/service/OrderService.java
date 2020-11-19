package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order service defines common service methods for working with user orders
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see Order
 * @see User
 */
public interface OrderService {
    boolean addOrder (Order order, User user) throws ServiceException;

    boolean deleteOrder(User user, long orderId) throws ServiceException;

    boolean removeSongFromOrder(Order order, long songId, User user) throws ServiceException;

    boolean updateOrder (Order order, User user) throws ServiceException;

    List<Order> findUserOrders (long userId) throws ServiceException;

    Optional<Order> findOrderById (long id) throws ServiceException;

    Optional<String> prepareOrderDownloadLink(long currentOrderId) throws ServiceException;
}

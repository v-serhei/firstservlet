package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.OrderDao;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.impl.OrderDaoImpl;
import by.verbitsky.servletdemo.model.dao.impl.SongDaoImpl;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public enum OrderServiceImpl implements OrderService {
    INSTANCE;

    @Override
    public boolean addOrder(Order order) throws ServiceException {
        if (order == null) {
            throw new ServiceException("OrderServiceImpl add order: received null order");
        }
        boolean result;
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao dao = new OrderDaoImpl();
            transaction.processTransaction(dao);
            result = (dao.create(order) && dao.createOrderDescription(order));
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl add new order: error while inserting new order to db");
        }
        return result;
    }

    @Override
    public Optional<Order> findOrderById(long orderId) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        Optional<Order> result;
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao dao = new OrderDaoImpl();
            transaction.processSimpleQuery(dao);
            result = dao.findEntityById(orderId);
        } catch (DaoException e) {
            throw new ServiceException("OrderDao: Error while searching order");
        }
        return result;
    }

    @Override
    public List<Order> findUserOrders(long userId) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao dao = new OrderDaoImpl();
            transaction.processSimpleQuery(dao);
            return dao.findOrdersByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("OrderService findUserOrders: error while searching user orders", e);
        }
    }

    @Override
    public boolean removeSongFromOrder(long orderId, long songId) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        boolean result = false;
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao orderDao = new OrderDaoImpl();
            ContentDao songDao = new SongDaoImpl();
            transaction.processTransaction(orderDao, songDao);
            Optional<AudioContent> song = songDao.findEntityById(songId);
            Optional<Order> order = orderDao.findEntityById(orderId);
            if (order.isPresent()) {
                BigDecimal songPrice = BigDecimal.ZERO;
                if (song.isPresent()) {
                    songPrice = ((Song) song.get()).getPrice();
                }
                order.get().setOrderPrice(order.get().getOrderPrice().subtract(songPrice));
                orderDao.removeOrderDescription(orderId, songId);
                result = orderDao.update(order.get());
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl: error while removing song from order");
        }
        return result;
    }


    @Override
    public boolean deleteOrder(User user, long orderId) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao dao = new OrderDaoImpl();
            transaction.processTransaction(dao);
            Optional<Order> removedOrder = dao.findEntityById(orderId);
            if (removedOrder.isPresent()) {
                if (removedOrder.get().getUserId() == user.getUserId()) {
                    result = dao.delete(orderId);
                } else {
                    result = false;
                }
            } else {
                result = false;
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl: error while removing order");
        }
        return result;
    }

    @Override
    public boolean updateOrder(Order order) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao orderDao = new OrderDaoImpl();
            transaction.processSimpleQuery(orderDao);
            result = orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl: error while removing song from order");
        }
        return result;
    }

    public int getPaidOrderStatusId() {
        return OrderStatus.PAID.getOrderStatusId();
    }

    private ProxyConnection askConnectionFromPool() throws ServiceException {
        ProxyConnection result;
        try {
            result = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("OrderServiceImpl: error while receiving connection from pool");
        }
        return result;
    }
}

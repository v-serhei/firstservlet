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
import by.verbitsky.servletdemo.util.FileUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public enum OrderServiceImpl implements OrderService {
    INSTANCE;

    @Override
    public boolean addOrder(Order order, User user) throws ServiceException {
        if (order == null || user == null) {
            throw new ServiceException("OrderServiceImpl add order: received null order");
        }
        boolean createResult;
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao dao = new OrderDaoImpl();
            transaction.processTransaction(dao);
            order.setOrderPrice(calculateOrderPrice(order, user.getDiscount()));
            createResult = (dao.create(order) && dao.createOrderDescription(order));
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl add new order: error while inserting new order to db");
        }
        return createResult;
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
    public boolean removeSongFromOrder(Order order, long songId, User user) throws ServiceException {
        if (order == null || user == null) {
            throw new ServiceException("OrderServiceImpl removeSongFromOrder: received null parameters");
        }
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao orderDao = new OrderDaoImpl();
            ContentDao songDao = new SongDaoImpl();
            transaction.processTransaction(orderDao, songDao);
            Optional<AudioContent> song = songDao.findEntityById(songId);
            if (!song.isPresent()) {
                return false;
            }
            boolean updateDescription = orderDao.removeOrderDescription(order.getOrderId(), songId);
            if (updateDescription) {
                //decrease order price
                order.removeSongById(songId);
                order.setOrderPrice(calculateOrderPrice(order, user.getDiscount()));
            }
            boolean updateOrder = orderDao.update(order);
            if (updateOrder) {
                transaction.commitTransaction();
                return true;
            }else {
                transaction.rollbackTransaction();
                //roll back order price
                order.addSong((Song)song.get());
                order.setOrderPrice(calculateOrderPrice(order, user.getDiscount()));
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl: error while removing song from order");
        }

    }


    @Override
    public boolean deleteOrder(User user, long orderId) throws ServiceException {
        if (user == null) {
            throw new ServiceException("OrderServiceImpl delete order: received null user");
        }
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
    public boolean updateOrder(Order order, User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("OrderServiceImpl updateOrder: received null parameters");
        }
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao orderDao = new OrderDaoImpl();
            transaction.processSimpleQuery(orderDao);
            order.setOrderPrice(calculateOrderPrice(order, user.getDiscount()));
            result = orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl: error while removing song from order");
        }
        return result;
    }

    @Override
    public Optional<String> prepareOrderDownloadLink(long currentOrderId) throws ServiceException{
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            OrderDao orderDao = new OrderDaoImpl();
            transaction.processSimpleQuery(orderDao);
            Optional<Order> currentOrder = orderDao.findEntityById(currentOrderId);
            if (currentOrder.isPresent()) {
                Set<Song> orderList = currentOrder.get().getOrderList();
                List<String> pathList = orderList.stream().map(Song::getFilePath).collect(Collectors.toList());
                return FileUtil.generateZipFileForOrder(pathList, currentOrderId);
            }
        } catch (DaoException e) {
            throw new ServiceException("OrderServiceImpl: error while removing song from order");
        }
        return Optional.empty();
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

    private BigDecimal calculateOrderPrice(Order order, int discountValue) {
        Set<Song> orderList = order.getOrderList();
        BigDecimal result = BigDecimal.ZERO;
        for (Song song : orderList) {
            result = result.add(song.getPrice());
        }
        return result.subtract(result.multiply(BigDecimal.valueOf(discountValue * 1.0 / 100)));
    }
}

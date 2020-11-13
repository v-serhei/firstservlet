package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.model.dao.OrderFactory;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.OrderDao;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OrderDaoImpl implements OrderDao {
    private Connection connection;

    private static final String INSERT_ORDER =
            "Insert Into orders (user_id, order_summ, order_date, order_status) values (?, ?, ?, ?);";

    private static final String INSERT_ORDER_DESCRIPTION =
            "Insert Into orders_description (order_id, song_id) values ( ?, ?);";

    private static final String REMOVE_SONG_FROM_ORDER =
            "Delete from orders_description where order_id = ? and song_id = ?";

    private static final String SELECT_ORDER_BY_ID =
            "Select orders.order_id," +
                    "       orders.user_id," +
                    "       orders.order_summ," +
                    "       orders.order_date," +
                    "       orders.order_status," +
                    "       so.song_id as song_id," +
                    "       song_title," +
                    "       upload_date," +
                    "       song_price," +
                    "       singer_name," +
                    "       album_title," +
                    "       album.creation_date as album_date," +
                    "       genre_name " +
            "from orders " +
                    "       left join orders_description as description on orders.order_id = description.order_id" +
                    "       left join songs as so on so.song_id = description.song_id" +
                    "       left join singers as si on so.singer_id = si.singer_id" +
                    "       left join albums as album on so.album_id = album.album_id" +
                    "       left join genres as ge on so.genre_id = ge.genre_id " +
            "where orders.order_id = ?;";

    private static final String UPDATE_ORDER =
            "Update orders Set orders.order_summ = ?, orders.order_status = ?, order_date = ? " +
            "where orders.order_id = ?;";

    private static final String DELETE_ORDER =
           "Delete from orders where orders.order_id = ?;";

    private static final String FIND_USER_ORDERS_BY_USER_ID =
            "Select orders.order_id," +
                    "       orders.user_id," +
                    "       orders.order_summ," +
                    "       orders.order_date," +
                    "       orders.order_status," +
                    "       so.song_id as song_id " +
                    "from orders " +
                    "       left join songs as so on so.song_id " +
                    "where orders.user_id = ? group by orders.order_id;";

    private static final OrderFactory<Order> factory = new OrderFactoryImpl();


    @Override
    public void removeOrderDescription(long orderId, long songId) throws DaoException {
        if (connection == null) {
            throw new DaoException("OrderDaoImpl createOrder: connection is null");
        }
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_SONG_FROM_ORDER)) {
            statement.setLong(1, orderId);
            statement.setLong(2, songId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(Order order) throws DaoException {
        if (connection == null) {
            throw new DaoException("OrderDaoImpl createOrder: connection is null");
        }
        if (order == null) {
            throw new DaoException("OrderDaoImpl createOrder: received null order");
        }
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.setBigDecimal(2, order.getOrderPrice());
            statement.setDate(3, Date.valueOf(order.getOrderDate()));
            statement.setInt(4, order.getOrderStatusId());
            int res = statement.executeUpdate();
            if (res > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getLong(1));
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("OrderDao create order: error while creating order in db");
        }
        return result;
    }

    @Override
    public boolean createOrderDescription(Order order) throws DaoException {
        if (connection == null) {
            throw new DaoException("OrderDaoImpl createOrderDescription: connection is null");
        }
        if (order == null) {
            throw new DaoException("OrderDaoImpl createOrderDescription: received null order");
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_DESCRIPTION)) {
            Set<Song> orderList = order.getOrderList();
            for (Song song : orderList) {
                statement.setLong(1, order.getOrderId());
                statement.setLong(2, song.getId());
                int res = statement.executeUpdate();
                if (res == 0) {
                    throw new DaoException("OrderDao createOrderDescription: error while creating order description");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public List<Order> findOrdersByUserId(long userId) throws DaoException {
        if (connection == null) {
            throw new DaoException("OrderDaoImpl findOrdersByUserId: connection is null");
        }
        try (PreparedStatement statement = connection.prepareStatement(FIND_USER_ORDERS_BY_USER_ID)){
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return factory.createSimpleOrders(resultSet);
        } catch (SQLException e) {
            throw new DaoException("OrderDaoImpl: error while searching user orders", e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findEntityById(Long orderId) throws DaoException {
        if (connection == null) {
            throw new DaoException("OrderDaoImpl createOrder: connection is null");
        }
        Optional<Order> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = factory.createOrder(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("OrderDaoImpl update order: SQL error while searching order", e);
        }
        return result;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        if (connection == null) {
            throw new DaoException("OrderDaoImpl update order: connection is null");
        }
        boolean result;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)){
            statement.setBigDecimal(1, order.getOrderPrice());
            statement.setInt(2, order.getOrderStatusId());
            statement.setDate(3, Date.valueOf(order.getOrderDate()));
            statement.setLong(4, order.getOrderId());
            int count = statement.executeUpdate();
            result = count > 0;
        } catch (SQLException e) {
            throw new DaoException("OrderDaoImpl update order: SQL error while updating order", e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        if (connection == null) {
            throw new DaoException("OrderDaoImpl delete order: connection is null");
        }
        boolean result;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)){
            statement.setLong(1, id);
            int count = statement.executeUpdate();
            result = count > 0;
        } catch (SQLException e) {
            throw new DaoException("OrderDaoImpl update order: SQL error while deleting order", e);
        }
        return result;
    }

    @Override
    public void setConnection(Connection connection) throws DaoException {
        if (connection != null) {
            this.connection = connection;
        } else {
            throw new DaoException("SetConnection: received null connection");
        }
    }
}
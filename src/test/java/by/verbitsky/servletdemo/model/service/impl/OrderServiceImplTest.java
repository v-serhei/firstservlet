package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.OrderDao;
import by.verbitsky.servletdemo.model.dao.impl.OrderDaoImpl;
import by.verbitsky.servletdemo.model.pool.ConnectionPool;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.DaoFactory;
import by.verbitsky.servletdemo.model.service.OrderService;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.powermock.api.mockito.PowerMockito.when;

public class OrderServiceImplTest {
    private OrderService service;
    private OrderDao dao;
    private ConnectionPool<ProxyConnection> pool;
    private Order order1;
    private Order order2;
    private Order order3;
    private User user1;
    private User user2;
    private User user3;
    private ProxyConnection connection;
    private DaoFactory daoFactory;


    @BeforeClass
    public void setUp() throws PoolException, DaoException {
        daoFactory = Mockito.mock(DaoFactoryImpl.class);
        dao = Mockito.mock(OrderDaoImpl.class);
        when(daoFactory.getOrderDao()).thenReturn(dao);
        Whitebox.setInternalState(OrderServiceImpl.class, "daoFactory", daoFactory);

        service = OrderServiceImpl.INSTANCE;
        pool = ConnectionPoolImpl.getInstance();
        pool.initConnectionPool();
        connection = pool.getConnection();
        dao.setConnection(connection);

        user1 = new User();
        user2 = new User();
        user3 = new User();

        user1.setUserId(1L);
        user2.setUserId(2L);
        user3.setUserId(3L);

        Song song1 = new Song();
        song1.setId(1);
        song1.setSongTitle("TestSong");
        song1.setSingerId(1);
        song1.setAlbumId(1);
        song1.setGenreId(1);
        song1.setFilePath("path1");
        song1.setPrice(new BigDecimal(1));
        song1.setUploadDate(LocalDate.now());

        Song song2 = new Song();
        song2.setId(2);
        song2.setSongTitle("TestSong2");
        song2.setSingerId(2);
        song2.setAlbumId(2);
        song2.setGenreId(2);
        song2.setFilePath("path2");
        song2.setPrice(new BigDecimal(2));
        song2.setUploadDate(LocalDate.now());

        Song song3 = new Song();
        song3.setId(3);
        song3.setSongTitle("TestSong3");
        song3.setSingerId(3);
        song3.setAlbumId(3);
        song3.setGenreId(3);
        song3.setFilePath("path3");
        song3.setPrice(new BigDecimal(3));
        song3.setUploadDate(LocalDate.now());

        order1 = new Order();
        order2 = new Order();
        order3 = new Order();

        order1.addSong(song1);
        order2.addSong(song2);
        order3.addSong(song3);
    }

    @AfterClass
    public void tearDown() throws PoolException {
        pool.releaseConnection(connection);
        pool.shutdownPool();
        dao = null;
        daoFactory = null;
        pool = null;
        service = null;
        user1 = null;
        user2 = null;
        user3 = null;
        order1 = null;
        order2 = null;
        order3 = null;
    }

    @Test
    public void testAddOrderPositive() throws ServiceException, DaoException {
        when(dao.create(order1)).thenReturn(true);
        when(dao.createOrderDescription(order1)).thenReturn(true);
        Assert.assertTrue(service.addOrder(order1, user1));
    }

    @Test
    public void testAddOrderNegativeDaoFalseResult() throws ServiceException, DaoException {
        when(dao.create(order2)).thenReturn(false);
        when(dao.createOrderDescription(order2)).thenReturn(false);
        Assert.assertFalse(service.addOrder(order2, user2));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddOrderNegativeNullParameter() throws ServiceException {
        service.addOrder(null, null);
    }

    @Test
    public void testFindOrderByIdPositive() throws ServiceException, DaoException {
        when(dao.findEntityById(1L)).thenReturn(Optional.of(order1));
        Optional<Order> orderById = service.findOrderById(1L);
        Assert.assertTrue(orderById.isPresent());
    }

    @Test
    public void testFindOrderByIdNegativeEmpty() throws ServiceException, DaoException {
        when(dao.findEntityById(2L)).thenReturn(Optional.empty());
        Optional<Order> orderById = service.findOrderById(2L);
        Assert.assertFalse(orderById.isPresent());
    }

    @Test
    public void testFindUserOrdersPositive() throws ServiceException, DaoException {
        List<Order> daoResultList = new ArrayList<>();
        daoResultList.add(order1);
        daoResultList.add(order2);
        daoResultList.add(order3);
        when(dao.findOrdersByUserId(1L)).thenReturn(daoResultList);
        List<Order> userOrders = service.findUserOrders(1L);
        Assert.assertTrue(userOrders.size() > 0);
    }

    @Test
    public void testDeleteOrderPositive() throws ServiceException, DaoException {
        Order order = new Order();
        order.setUserId(1L);
        when(dao.delete(1L)).thenReturn(true);
        when(dao.findEntityById(1L)).thenReturn(Optional.of(order));
        Assert.assertTrue(service.deleteOrder(user1, 1L));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteOrderNegativeNullParameter() throws ServiceException{
        service.deleteOrder(null, 1L);
    }

    @Test
    public void testUpdateOrderPositive() throws ServiceException, DaoException {
        when(dao.update(order1)).thenReturn(true);
        Assert.assertTrue(service.updateOrder(order1, user1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateOrderNegativeNullParameter() throws ServiceException {
        service.updateOrder(null, null);
    }

    @Test
    public void testPrepareOrderDownloadLinkPositive() throws ServiceException, DaoException {
        String expectedPath = "E:\\Audiobox\\orders\\order__1\\order__1.zip";
        when(dao.findEntityById(1L)).thenReturn(Optional.of(order1));
        Optional<String> s = service.prepareOrderDownloadLink(1L);
        Assert.assertEquals(s.get(), expectedPath);
    }
}
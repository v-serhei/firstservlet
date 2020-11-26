package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.UserDao;
import by.verbitsky.servletdemo.model.dao.impl.UserDaoImpl;
import by.verbitsky.servletdemo.model.pool.ConnectionPool;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.DaoFactory;
import by.verbitsky.servletdemo.model.service.UserService;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.powermock.api.mockito.PowerMockito.when;

public class UserServiceImplTest {
    private UserService service;
    private UserDao dao;
    private ConnectionPool<ProxyConnection> pool;
    private User user1;
    private User user2;
    private User user3;
    private ProxyConnection connection;
    private DaoFactory daoFactory;

    @BeforeClass
    public void setUp() throws PoolException, DaoException {
        daoFactory = Mockito.mock(DaoFactoryImpl.class);
        dao = Mockito.mock(UserDaoImpl.class);
        when(daoFactory.getUserDao()).thenReturn(dao);
        Whitebox.setInternalState(UserServiceImpl.class, "daoFactory", daoFactory);

        service = UserServiceImpl.INSTANCE;
        pool = ConnectionPoolImpl.getInstance();
        pool.initConnectionPool();
        connection = pool.getConnection();
        dao.setConnection(connection);

        user1 = new User();
        user2 = new User();
        user3 = new User();

        user1.setUserId(1L);
        user1.setBlockedStatus(0);
        user2.setUserId(2L);
        user3.setUserId(3L);

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
    }


    @Test
    public void testGetAdminRoleId() {
        int expected = 99;
        int actual = service.getAdminRoleId();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetRoleIdByDescriptionPositive() throws ServiceException {
        int expected = 99;
        int actual = service.getRoleIdByDescription("ADMIN");
        Assert.assertEquals(expected, actual);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testGetRoleIdByDescriptionNegative() throws ServiceException {
        service.getRoleIdByDescription("EMPTY");
    }

    @Test
    public void testGetBlockedStatusIdByDescription() {
        int expected = 1;
        int actual = service.getBlockedStatusIdByDescription("Blocked");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetRoleList() {
        List<String> roleList = service.getRoleList();
        Assert.assertTrue(roleList.size() > 0);
    }

    @Test
    public void testGetRoleNameById() {
        String expected = "ADMIN";
        String actual = service.getRoleNameById(99);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllUsersPositive() throws ServiceException, DaoException {
        List<User> daoResultList = new ArrayList<>();
        daoResultList.add(user1);
        when(dao.findAll()).thenReturn(daoResultList);
        List<User> allUsers = service.findAllUsers();
        Assert.assertTrue(allUsers.size() > 0);
    }

    @Test
    public void testFindUserByNamePositive() throws DaoException, ServiceException {
        String userName = "UserName";
        when(dao.findUserByName(userName.toLowerCase())).thenReturn(Optional.of(user1));
        Assert.assertTrue(service.findUserByName(userName).isPresent());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindUserByNamePNegativeNullParameter() throws ServiceException {
        service.findUserByName(null);
    }

    @Test
    public void testCheckBlockedStatus() throws DaoException, ServiceException {
        String userName = "UserName";
        when(dao.findUserByName(userName.toLowerCase())).thenReturn(Optional.of(user1));
        Assert.assertFalse(service.checkBlockedStatus(userName));
    }

    @Test
    public void testUpdateUserPositive() throws DaoException, ServiceException {
        when(dao.update(user1)).thenReturn(true);
        Assert.assertTrue(service.updateUser(user1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateUserNegative() throws ServiceException {
        service.updateUser(null);
    }
}
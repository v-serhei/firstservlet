package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.OrderDao;
import by.verbitsky.servletdemo.model.dao.UserDao;

public interface DaoFactory {
    UserDao getUserDao();

    OrderDao getOrderDao();

    ContentDao getContentDao(ContentType type) throws ServiceException;
}

package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.OrderDao;
import by.verbitsky.servletdemo.model.dao.UserDao;
import by.verbitsky.servletdemo.model.dao.impl.*;
import by.verbitsky.servletdemo.model.service.DaoFactory;

class DaoFactoryImpl implements DaoFactory {
    public DaoFactoryImpl() {
    }

    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public OrderDao getOrderDao() {
        return new OrderDaoImpl();
    }

    @Override
    public ContentDao getContentDao(ContentType type) throws ServiceException {
        switch (type) {
            case SONG: {
                return new SongDaoImpl();
            }
            case SINGER: {
                return new SingerDaoImpl();
            }
            case GENRE: {
                return new GenreDaoImpl();
            }
            case REVIEW: {
                return new ReviewDaoImpl();
            }
            case COMPILATION: {
                return new CompilationDaoImpl();
            }
            case ALBUM: {
                return new AlbumDaoImpl();
            }
            default: {
                throw new ServiceException("ContentServiceImpl findFilteredContent: unsupported content tye " + type);
            }
        }
    }
}

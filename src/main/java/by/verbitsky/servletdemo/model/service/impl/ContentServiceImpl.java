package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.impl.GenreDaoImpl;
import by.verbitsky.servletdemo.model.dao.impl.SingerDaoImpl;
import by.verbitsky.servletdemo.model.dao.impl.SongDaoImpl;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ContentType;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;

import java.util.List;

public enum ContentServiceImpl implements ContentService {
    INSTANCE;

    @Override
    public long calculateItemsCount(ContentType type, ContentFilter filter) throws ServiceException {
        if (type == null) {
            throw new ServiceException("ContentServiceImpl: null parameter Content type");
        }
        long result;
        ProxyConnection connection;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("ContentServiceImpl: error while receiving connection from pool", e);
        }
        ContentDao dao = null;
        switch (type) {
            case SONG: {
                dao = new SongDaoImpl();
                break;
            }
            // TODO: 25.10.2020 дописать остальные дао
        }
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(dao);
            result = dao.calculateRowCount(filter);
        } catch (DaoException e) {
            throw new ServiceException("ContentServiceImpl: processing calculate Content items count DaoException error", e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findAllContent(ContentType type) throws ServiceException {
        List<AudioContent> result;
        ProxyConnection connection;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("ContentServiceImpl: error while receiving connection from pool");
        }
        ContentDao dao = null;
        switch (type) {
            case SONG: {
                dao = new SongDaoImpl();
                break;
            }
            case SINGER: {
                dao = new SingerDaoImpl();
                break;
            }
            case GENRE: {
                dao = new GenreDaoImpl();
                break;
            }
            // TODO: 25.10.2020  Дописать остальные дао когда добавлю
        }
        try (Transaction transaction = new Transaction(connection)) {
            dao.setConnection(connection);
            transaction.processSimpleQuery(dao);
            result = dao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("ContentServiceImpl: error while searching singers", e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findFilteredContent(ContentFilter filter) throws ServiceException {
        List<AudioContent> result;
        ContentDao dao = null;
        ProxyConnection connection;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("ContentServiceImpl: error while receiving connection from pool");
        }
        switch (filter.getContentType()) {
            case SONG: {
                dao = new SongDaoImpl();
                break;
            }
        }
        try (Transaction transaction = new Transaction(connection)) {
            long offset = filter.getPageNumber() * filter.getItemPerPage() - filter.getItemPerPage();
            dao.setConnection(connection);
            transaction.processSimpleQuery(dao);
            result = dao.findFilteredContent(offset, filter.getItemPerPage(), filter);
        } catch (DaoException e) {
            throw new ServiceException("ContentServiceImpl: error while searching singers", e);
        }
        return result;
    }
}
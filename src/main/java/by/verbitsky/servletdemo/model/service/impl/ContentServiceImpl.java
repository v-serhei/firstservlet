package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.BaseDao;
import by.verbitsky.servletdemo.model.dao.ContentType;
import by.verbitsky.servletdemo.model.dao.SongDao;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.impl.GenreDaoImpl;
import by.verbitsky.servletdemo.model.dao.impl.SingerDaoImpl;
import by.verbitsky.servletdemo.model.dao.impl.SongDaoImpl;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;

import java.util.ArrayList;
import java.util.List;

public enum ContentServiceImpl implements ContentService<AudioContent> {
    INSTANCE;

    @Override
    public List<AudioContent> findSongs(int songsPerPage, int pageNumber) throws ServiceException {
        List<AudioContent> list = new ArrayList<>();
        ProxyConnection connection;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("ContentServiceImpl: error while receiving connection from pool");
        }
        try (Transaction transaction = new Transaction(connection)) {
            SongDao songDao = new SongDaoImpl();
            songDao.setConnection(connection);
            transaction.processSimpleQuery(songDao);
            long offset = pageNumber * songsPerPage - songsPerPage;
            list.addAll(songDao.findEntity(offset, songsPerPage));
        } catch (DaoException e) {
            throw new ServiceException("ContentServiceImpl: error while searching songs", e);
        }
        return list;
    }

    @Override
    public long calculateItemsCount(ContentType type) throws ServiceException {
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
        BaseDao<? extends AudioContent> dao = null;
        switch (type) {
            case SONG: {
                dao = new SongDaoImpl();
                break;
            }
            //todo add DAO
            case ALBUM: {
                dao = new SongDaoImpl();
                break;
            }
            case COMPILATION: {
                dao = new SongDaoImpl();
                break;
            }
        }
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(dao);
            result = dao.calculateRowCount();
        } catch (DaoException e) {
            throw new ServiceException("ContentServiceImpl: processing calculate Content items count DaoException error", e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findSongByTitle(String title) {
        return null;
    }

    public List<AudioContent> findAllContent(ContentType type) throws ServiceException {
        List <AudioContent> result;
        ProxyConnection connection;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("ContentServiceImpl: error while receiving connection from pool");
        }
        BaseDao<AudioContent> dao = null;

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
}
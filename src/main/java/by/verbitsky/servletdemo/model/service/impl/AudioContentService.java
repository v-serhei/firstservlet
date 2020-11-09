package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.impl.*;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;

import java.util.List;
import java.util.Optional;

public enum AudioContentService implements ContentService {
    INSTANCE;
    private static final int DEFAULT_ITEMS_PER_PAGE = 1;
    private static final int DEFAULT_PAGE_NUMBER = 1;


    @Override
    public long calculateItemsCount(ContentFilter filter) throws ServiceException {
        if (filter == null) {
            throw new ServiceException("AudioContentService calculateItemsCount: received null filter");
        }
        ProxyConnection connection = askConnectionFromPool();
        long result;
        try (Transaction transaction = new Transaction(connection)) {
            ContentDao dao = defineDaoByContentType(filter.getContentType());
            transaction.processSimpleQuery(dao);
            result = dao.calculateRowCount(filter);
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService: processing calculate Content items count DaoException error", e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findAllContent(ContentType type) throws ServiceException {
        if (type == null) {
            throw new ServiceException("AudioContentService: null parameter Content type");
        }
        List<AudioContent> result;
        ProxyConnection connection = askConnectionFromPool();
        ContentDao dao = defineDaoByContentType(type);
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(dao);
            result = dao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService: error while searching singers", e);
        }
        return result;
    }

    @Override
    public Optional<AudioContent> findContentById(ContentType type, long id) throws ServiceException {
        if (type == null) {
            throw new ServiceException("AudioContentService findContentById: null parameter Content type");
        }
        ProxyConnection connection = askConnectionFromPool();
        Optional<AudioContent> result;
        try (Transaction transaction = new Transaction(connection)) {
            ContentDao dao = defineDaoByContentType(type);
            transaction.processSimpleQuery(dao);
            result = dao.findEntityById(id);
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService: error while searching content by id", e);
        }
        return result;
    }

    @Override
    public List<String> findContentProperties(ContentType contentType) throws ServiceException {
        if (contentType == ContentType.COMPILATION) {
            ContentDao dao = new CompilationDao();
            ProxyConnection connection = askConnectionFromPool();
            try (Transaction transaction = new Transaction(connection)) {
                transaction.processSimpleQuery(dao);
                return dao.findContentProperties();
            } catch (DaoException e) {
                throw new ServiceException("AudioContentService: findContentProperties: error while receiving properties from db");
            }
        }
        throw new ServiceException("AudioContentService: findContentProperties received unsupported content type");
    }

    @Override
    public boolean createReview(User user, String songTitle, String singerName, String reviewText) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        SongFilter songFilter = new SongFilter();
        songFilter.setSingerName(singerName);
        songFilter.setSongTitle(songTitle);
        songFilter.setItemPerPage(DEFAULT_ITEMS_PER_PAGE);
        songFilter.setPageNumber(DEFAULT_PAGE_NUMBER);
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            ContentDao dao = new ReviewDaoImpl();
            transaction.processTransaction(dao);
            List<AudioContent> songs = findFilteredContent(songFilter);
            if (songs.size() == 1) {
                long songId = songs.get(0).getId();
                Review review = new Review();
                review.setReviewText(reviewText);
                review.setSongId(songId);
                review.setUserId(user.getUserId());
                result = dao.create(review);
            } else {
                result = false;
            }
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService: error while searching content by id", e);
        }
        return result;
    }

    public List<AudioContent> findUserReviews(User user) throws ServiceException {
        if (user.getUserId() == 0 ) {
            throw new ServiceException("AudioContentService find user Reviews: user id = 0");
        }
        ContentDao dao = new ReviewDaoImpl();
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(dao);
            return dao.findContentByUser(user);
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService find user Reviews: error while searching user reviews", e);
        }
    }

    @Override
    public boolean deleteContentById(ContentType type, long contentId) throws ServiceException {
        if (type == null || contentId == 0) {
            throw new ServiceException("AudioContentService findContentById: null parameter Content type or content id = 0");
        }
        ContentDao dao = defineDaoByContentType(type);
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(dao);
            result = dao.delete(contentId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService deleteContentById: error while deleting content by id",e);
        }
        return result;
    }


    @Override
    public List<AudioContent> findFilteredContent(ContentFilter filter) throws ServiceException {
        if (filter == null) {
            throw new ServiceException("AudioContentService: received null filter");
        }
        List<AudioContent> result;
        ContentDao dao = defineDaoByContentType(filter.getContentType());
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            long offset = filter.getPageNumber() * filter.getItemPerPage() - filter.getItemPerPage();
            transaction.processSimpleQuery(dao);
            result = dao.findFilteredContent(offset, filter.getItemPerPage(), filter);
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService: error while searching content", e);
        }
        return result;
    }


    private ContentDao defineDaoByContentType(ContentType type) throws ServiceException {
        ContentDao dao;
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
            case REVIEW: {
                dao = new ReviewDaoImpl();
                break;
            }
            case COMPILATION: {
                dao = new CompilationDao();
                break;
            }
            default: {
                throw new ServiceException("ContentServiceImpl findFilteredContent: unsupported content tye " + type);
            }
        }
        return dao;
    }


    private ProxyConnection askConnectionFromPool() throws ServiceException {
        ProxyConnection result;
        try {
            result = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("ContentServiceImpl: error while receiving connection from pool");
        }
        return result;
    }
}
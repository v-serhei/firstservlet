package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.*;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.DaoFactory;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum AudioContentService implements ContentService {
    INSTANCE;
    private static final int DEFAULT_ITEMS_PER_PAGE = 1;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static DaoFactory daoFactory = new DaoFactoryImpl();

    @Override
    public long calculateItemsCount(ContentFilter filter) throws ServiceException {
        if (filter == null) {
            throw new ServiceException("AudioContentService calculateItemsCount: received null filter");
        }
        ProxyConnection connection = askConnectionFromPool();
        long result;
        ContentDao dao = daoFactory.getContentDao(filter.getContentType());
        try (Transaction transaction = new Transaction(connection)) {
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
        ContentDao dao = daoFactory.getContentDao(type);
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
        ContentDao dao = daoFactory.getContentDao(type);
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(dao);
            result = dao.findEntityById(id);
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService: error while searching content by id", e);
        }
        return result;
    }

    @Override
    public List<String> findContentDescription(ContentType contentType) throws ServiceException {
        if (contentType == ContentType.COMPILATION) {
            ContentDao dao = daoFactory.getContentDao(contentType);
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
        if (user == null || songTitle == null || singerName == null || reviewText == null) {
            throw new ServiceException("AudioContentService create review: received null parameters");
        }
        if (songTitle.isEmpty() || singerName.isEmpty()) {
            throw new ServiceException("AudioContentService create review: singer name or song title is empty");
        }
        ProxyConnection connection = askConnectionFromPool();
        SongFilter songFilter = new SongFilter();
        songFilter.setSingerName(singerName);
        songFilter.setSongTitle(songTitle);
        songFilter.setItemPerPage(DEFAULT_ITEMS_PER_PAGE);
        songFilter.setPageNumber(DEFAULT_PAGE_NUMBER);
        boolean result;
        ContentDao dao = daoFactory.getContentDao(ContentType.REVIEW);
        try (Transaction transaction = new Transaction(connection)) {
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

    @Override
    public List<AudioContent> findUserReviews(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("AudioContentService find user Reviews: received null user");
        }
        ContentDao dao = daoFactory.getContentDao(ContentType.REVIEW);
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
        if (type == null) {
            throw new ServiceException("AudioContentService findContentById: null parameter Content type");
        }
        ContentDao dao = daoFactory.getContentDao(type);
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(dao);
            result = dao.delete(contentId);
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService deleteContentById: error while deleting content", e);
        }
        return result;
    }

    @Override
    public Optional<AudioContent> findContentByTitle(ContentType type, String title) throws ServiceException {
        if (type==null || title == null) {
            throw new ServiceException("AudioContentService findContentByTitle: null parameter title");
        }
        ContentDao dao = daoFactory.getContentDao(type);
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(dao);
            return dao.findContentByTitle(title);
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService findContentByTitle: error while searching content", e);
        }
    }

    @Override
    public boolean updateContent(ContentType contentType, AudioContent content) throws ServiceException {
        if (contentType == null || content == null) {
            throw new ServiceException("AudioContentService update content: null parameter content");
        }
        ContentDao dao = daoFactory.getContentDao(contentType);
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(dao);
            boolean updateResult = dao.update(content);
            transaction.commitTransaction();
            return updateResult;
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService update content: error while updating content", e);
        }
    }

    @Override
    public boolean createSong(Song song) throws ServiceException {
        if (song == null) {
            throw new ServiceException("Audio content service createSong: received null song");
        }
        Optional<AudioContent> singerById = findContentById(ContentType.SINGER, song.getSingerId());
        Optional<AudioContent> albumById = findContentById(ContentType.ALBUM, song.getAlbumId());
        Optional<AudioContent> genreById = findContentById(ContentType.GENRE, song.getGenreId());
        if (singerById.isPresent() && albumById.isPresent() && genreById.isPresent()) {
            List<AudioContent> oSong = findSongByFilter(song, (Singer)singerById.get(), (Album)albumById.get());
            if (oSong.size() > 0) {
                return false;
            }
            ContentDao dao = daoFactory.getContentDao(ContentType.SONG);
            ProxyConnection connection = askConnectionFromPool();
            try (Transaction transaction = new Transaction(connection)) {
                transaction.processSimpleQuery(dao);
                return dao.create(song);
            } catch (DaoException e) {
                throw new ServiceException("AudioContentService update content: error while updating song", e);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean updateSong(Song song) throws ServiceException {
        if (song == null) {
            throw new ServiceException("Audio content service updateSong: received null song");
        }
        Optional<AudioContent> songById = findContentById(ContentType.SONG, song.getId());
        Optional<AudioContent> singerById = findContentById(ContentType.SINGER, song.getSingerId());
        Optional<AudioContent> albumById = findContentById(ContentType.ALBUM, song.getAlbumId());
        Optional<AudioContent> genreById = findContentById(ContentType.GENRE, song.getGenreId());
        if (songById.isPresent() && singerById.isPresent() && albumById.isPresent() && genreById.isPresent()) {
            List<AudioContent> oSong = findSongByFilter(song, (Singer)singerById.get(), (Album)albumById.get());
            if (oSong.size() > 0) {
                return false;
            }
            ContentDao dao = daoFactory.getContentDao(ContentType.SONG);
            ProxyConnection connection = askConnectionFromPool();
            try (Transaction transaction = new Transaction(connection)) {
                transaction.processSimpleQuery(dao);
                return dao.update(song);
            } catch (DaoException e) {
                throw new ServiceException("AudioContentService update content: error while updating song", e);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean createSinger(String singerName) throws ServiceException {
        if (singerName == null || singerName.isEmpty()) {
            throw new ServiceException("Audio content service createSinger: received null singer name");
        }
        Singer singer = new Singer();
        singer.setSingerName(singerName);
        ContentDao singerDao = daoFactory.getContentDao(ContentType.SINGER);
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(singerDao);
            Optional<AudioContent> singerDB = singerDao.findContentByTitle(singerName);
            if (singerDB.isPresent()) {
                return false;
            }
            boolean createResult = singerDao.create(singer);
            transaction.commitTransaction();
            return createResult;
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService create singer: error while creating singer", e);
        }
    }

    @Override
    public boolean createAlbum(String albumTitle, long singerId, LocalDate albumDate) throws ServiceException {
        if (albumTitle == null || albumDate == null) {
            throw new ServiceException("Audio content service createAlbum: received null parameters");
        }
        Album album = new Album();
        album.setAlbumTitle(albumTitle);
        album.setSingerId(singerId);
        album.setAlbumDate(albumDate);
        ProxyConnection connection = askConnectionFromPool();
        ContentDao dao = daoFactory.getContentDao(ContentType.ALBUM);
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(dao);
            boolean creationResult = dao.create(album);
            transaction.commitTransaction();
            return creationResult;
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService create album: error while creating album", e);
        }
    }

    @Override
    public boolean createCompilation(String compilationTitle, String compilationType, LocalDate compilationDate, User user) throws ServiceException {
        if (compilationTitle == null || compilationDate == null
                || compilationType == null || user == null || user.getBasket().isEmpty()) {
            throw new ServiceException("Audio content service createCompilation: received null parameters");
        }
        Compilation compilation = new Compilation();
        compilation.setCompilationTitle(compilationTitle);
        compilation.setCompilationType(compilationType);
        compilation.setCompilationCreationDate(compilationDate);
        compilation.addAllSongs(new ArrayList<>(user.getBasket().getSongs()));
        ProxyConnection connection = askConnectionFromPool();
        ContentDao dao = daoFactory.getContentDao(ContentType.COMPILATION);
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(dao);
            boolean operationResult = (dao.create(compilation) && dao.createContentDescription(compilation));
            if (operationResult) {
                transaction.commitTransaction();
                user.getBasket().clear();
            } else {
                transaction.rollbackTransaction();
            }
            return operationResult;
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService create compilation: error while creating compilation", e);
        }
    }

    @Override
    public boolean createGenre(String genreName) throws ServiceException {
        if (genreName == null || genreName.isEmpty()) {
            throw new ServiceException("Audio content service createGenre: received null parameters");
        }
        Genre genre = new Genre();
        genre.setGenreName(genreName);
        ProxyConnection connection = askConnectionFromPool();
        ContentDao genreDao = daoFactory.getContentDao(ContentType.GENRE);
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(genreDao);
            Optional<AudioContent> genreDb = genreDao.findContentByTitle(genreName);
            if (genreDb.isPresent()) {
                return false;
            }
            boolean createResult = genreDao.create(genre);
            transaction.commitTransaction();
            return createResult;
        } catch (DaoException e) {
            throw new ServiceException("AudioContentService create genre: error while creating genre", e);
        }
    }

    @Override
    public List<AudioContent> findFilteredContent(ContentFilter filter) throws ServiceException {
        if (filter == null) {
            throw new ServiceException("AudioContentService: received null filter");
        }
        List<AudioContent> result;
        ContentDao dao = daoFactory.getContentDao(filter.getContentType());
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

    private ProxyConnection askConnectionFromPool() throws ServiceException {
        ProxyConnection result;
        try {
            result = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("ContentServiceImpl: error while receiving connection from pool");
        }
        return result;
    }

    private List<AudioContent> findSongByFilter(Song song, Singer singer, Album album) throws ServiceException {
        SongFilter filter = new SongFilter();
        filter.setSongTitle(song.getSongTitle());
        filter.setSingerName(singer.getSingerName());
        filter.setAlbumTitle(album.getAlbumTitle());
        filter.setItemPerPage(1);
        filter.setPageNumber(1);
        return findFilteredContent(filter);
    }
}
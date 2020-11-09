package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Genre;
import by.verbitsky.servletdemo.entity.impl.AudioContentFactory;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GenreDaoImpl extends AbstractDao implements ContentDao {
    private static final String SELECT_ALL_GENRE = "SELECT genre_id, genre_name FROM genres ORDER BY genre_name";
    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Genre>();


    @Override
    public List<String> findContentProperties() {
        return null;
    }

    @Override
    public List<AudioContent> findContentByUser(User user) throws DaoException {
        return null;
    }

    @Override
    public List<AudioContent> findAll() throws DaoException {
        if (connection == null) {
            throw new DaoException("GenreDao findAll: received null connection");
        }
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GENRE)) {
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.GENRE);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;

    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean update(AudioContent entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        return false;
    }

    @Override
    public long calculateRowCount(ContentFilter filter) throws DaoException {
        return 0;
    }

    @Override
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException {
        return null;
    }
}

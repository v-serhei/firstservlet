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

    private static final String SELECT_GENRE_BY_TITLE = "Select genre_id, genre_name " +
            "FROM genres " +
            "WHERE genre_name = ?";

    private static final String UPDATE_GENRE =
            "UPDATE genres SET genre_name = ? WHERE genres.genre_id = ?";

    private static final String INSERT_GENRE =
            "INSERT INTO genres (genre_name) VALUES (?);";

    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Genre>();


    @Override
    public List<AudioContent> findAll() throws DaoException {
        if (connection == null) {
            throw new DaoException("GenreDaoImpl findAll: received null connection");
        }
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GENRE)) {
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.GENRE);
        } catch (SQLException e) {
            throw new DaoException("GenreDaoImpl findAll: error while searching content", e);
        }
        return result;

    }

    @Override
    public Optional<AudioContent> findContentByTitle(String title) throws DaoException {
        if (connection == null) {
            throw new DaoException("GenreDaoImpl content by title: received null connection");
        }
        if (title == null) {
            return Optional.empty();
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GENRE_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = factory.createSingleContent(resultSet, ContentType.GENRE);
            }
        } catch (SQLException e) {
            throw new DaoException("GenreDaoImpl content by title: error while searching content", e);
        }
        return result;
    }

    @Override
    public boolean update(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("GenreDaoImpl update: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GENRE)) {
            statement.setString(1, ((Genre)entity).getGenreName());
            statement.setLong(2, entity.getId());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("GenreDaoImpl update: error while updating content", e);
        }
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("GenreDaoImpl create: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GENRE)) {
            statement.setString(1, ((Genre)entity).getGenreName());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("GenreDaoImpl create: error while creating content", e);
        }
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
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

    @Override
    public List<String> findContentProperties() {
        return null;
    }

    @Override
    public List<AudioContent> findContentByUser(User user) throws DaoException {
        return null;
    }
}

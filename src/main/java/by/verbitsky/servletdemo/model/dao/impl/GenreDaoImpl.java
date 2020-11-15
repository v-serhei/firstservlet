package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.model.dao.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Genre;
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

    private static final String SELECT_ALL_GENRE = "Select genre_id, genre_name from genres order by genre_name";

    private static final String SELECT_GENRE_BY_TITLE =
            "Select genre_id, genre_name " +
            "from genres " +
            "where genre_name = ?";

    private static final String UPDATE_GENRE =
            "Update genres Set genre_name = ? where genres.genre_id = ?";

    private static final String INSERT_GENRE =
            "Insert Into genres (genre_name) values (?);";

    private static final String SELECT_GENRE_BY_ID =
            "Select genre_id, genre_name " +
            "from genres " +
            "where genre_id = ?";


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
            Genre genre = (Genre) entity;
            statement.setString(1, genre.getGenreName());
            statement.setLong(2, entity.getId());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("GenreDaoImpl update: error while updating content", e);
        }catch (ClassCastException ex) {
            throw new DaoException("GenreDaoImpl update: error while casting entity to Genre.class", ex);
        }
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("GenreDaoImpl create: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GENRE)) {
            Genre genre = (Genre) entity;
            statement.setString(1, genre.getGenreName());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("GenreDaoImpl create: error while creating content", e);
        } catch (ClassCastException ex) {
            throw new DaoException("GenreDaoImpl update: error while casting entity to Genre.class", ex);
        }
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        if (connection == null) {
            throw new DaoException("GenreDaoImpl content by id: received null connection");
        }
        if (id == null) {
            return Optional.empty();
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_GENRE_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = factory.createSingleContent(resultSet, ContentType.GENRE);
            }
        } catch (SQLException e) {
            throw new DaoException("GenreDaoImpl content by id: error while searching content", e);
        }
        return result;
    }

    @Override
    public boolean createContentDescription(AudioContent entity) throws DaoException {
        return false;
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

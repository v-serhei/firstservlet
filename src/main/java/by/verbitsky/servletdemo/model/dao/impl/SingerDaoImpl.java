package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.model.dao.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Singer;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SingerDaoImpl extends AbstractDao implements ContentDao {
    private static final String SELECT_ALL_SINGERS = "Select singer_id, singer_name from singers order by singer_name";

    private static final String SELECT_SINGER_BY_NAME =
            "Select singer_id, singer_name " +
            "from singers " +
            "where singer_name = ?";

    private static final String UPDATE_SINGER =
            "Update singers Set singer_name = ? where singers.singer_id = ?";

    private static final String INSERT_SINGER =
            "Insert Into singers (singer_name) values (?);";

    private static final String SELECT_SINGER_BY_ID =
            "Select singer_id, singer_name " +
                    "from singers " +
                    "where singer_id = ?";

    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Singer>();

    @Override
    public Optional<AudioContent> findContentByTitle(String title) throws DaoException {
        if (connection == null) {
            throw new DaoException("SingerDaoImpl content by title: received null connection");
        }
        if (title == null) {
            return Optional.empty();
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SINGER_BY_NAME)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = factory.createSingleContent(resultSet, ContentType.SINGER);
            }
        } catch (SQLException e) {
            throw new DaoException("SingerDaoImpl content by title: error while searching content", e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findAll() throws DaoException {
        if (connection == null) {
            throw new DaoException("SingerDao findAll: received null connection");
        }
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SINGERS)) {
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.SINGER);
        } catch (SQLException e) {
            throw new DaoException("SingerDaoImpl findAll: error while searching content", e);
        }
        return result;
    }

    @Override
    public boolean update(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("SingerDao update: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SINGER)) {
            Singer singer = (Singer) entity;
            statement.setString(1, singer.getSingerName());
            statement.setLong(2, entity.getId());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("SingerDaoImpl update: error while updating content", e);
        }catch (ClassCastException ex) {
            throw new DaoException("SingerDaoImpl update: error while casting entity to Singer.class", ex);
        }
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("SingerDao update: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SINGER)) {
            Singer singer = (Singer) entity;
            statement.setString(1, singer.getSingerName());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("SingerDaoImpl update: error while creating content", e);
        }catch (ClassCastException ex) {
            throw new DaoException("SingerDaoImpl update: error while casting entity to Singer.class", ex);
        }
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        if (connection == null) {
            throw new DaoException("SingerDaoImpl content by id: received null connection");
        }
        if (id == null) {
            return Optional.empty();
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SINGER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = factory.createSingleContent(resultSet, ContentType.SINGER);
            }
        } catch (SQLException e) {
            throw new DaoException("SingerDaoImpl content by id: error while searching content", e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public long calculateRowCount(ContentFilter filter) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public List<AudioContent> findContentByUser(User user) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public List<String> findContentProperties() {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }

    @Override
    public boolean createContentDescription(AudioContent entity) {
        throw new UnsupportedOperationException ("Method not supported by current implementation");
    }
}
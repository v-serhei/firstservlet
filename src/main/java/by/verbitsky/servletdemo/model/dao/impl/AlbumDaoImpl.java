package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.model.dao.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Album;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AlbumDaoImpl extends AbstractDao implements ContentDao {

    private static final String SELECT_ALL_ALBUMS =
            "Select album_id, album_title, albums.singer_id, singer.singer_name, creation_date as album_date " +
            "from albums " +
                "left join singers singer on singer.singer_id = albums.singer_id " +
            "order by singer_id";

    private static final String SELECT_ALBUM_BY_TITLE =
            "Select album_id, album_title, albums.singer_id, singer.singer_name, creation_date as album_date " +
            "from albums " +
                "left join singers singer on singer.singer_id = albums.singer_id " +
            "where album_title = ?";

    private static final String UPDATE_ALBUM =
            "Update albums " +
            "Set album_title = ?," +
            "    creation_date = ?," +
            "    singer_id     = ? " +
            "where albums.album_id = ?;";

    private static final String INSERT_ALBUM =
            "Insert Into albums (album_title, creation_date, singer_id) values (?, ?, ?);";

    private static final String SELECT_ALBUM_BY_ID =
            "Select album_id, album_title, albums.singer_id, singer.singer_name, creation_date as album_date " +
                    "from albums " +
                    "left join singers singer on singer.singer_id = albums.singer_id " +
                    "where album_id = ?";

    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Album>();


    @Override
    public List<AudioContent> findAll() throws DaoException {
        if (connection == null) {
            throw new DaoException("albumDaoImpl findAll: received null connection");
        }
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ALBUMS)) {
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.ALBUM);
        } catch (SQLException e) {
            throw new DaoException("albumDaoImpl findAll: error while searching content", e);
        }
        return result;

    }

    @Override
    public Optional<AudioContent> findContentByTitle(String title) throws DaoException {
        if (connection == null) {
            throw new DaoException("albumDaoImpl content by title: received null connection");
        }
        if (title == null) {
            return Optional.empty();
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALBUM_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = factory.createSingleContent(resultSet, ContentType.ALBUM);
            }
        } catch (SQLException e) {
            throw new DaoException("albumDaoImpl content by title: error while searching content", e);
        }
        return result;
    }

    @Override
    public boolean update(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("albumDaoImpl update: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ALBUM)) {
            Album album = (Album) entity;
            statement.setString(1, album.getAlbumTitle());
            statement.setDate(2, Date.valueOf(album.getAlbumDate()));
            statement.setLong(3, album.getSingerId());
            statement.setLong(4, entity.getId());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("albumDaoImpl update: error while updating content", e);
        }catch (ClassCastException ex) {
            throw new DaoException("albumDaoImpl update: error while casting entity to Album.class", ex);
        }
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("albumDaoImpl create: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ALBUM)) {
            Album album = (Album) entity;
            statement.setString(1, album.getAlbumTitle());
            statement.setDate(2, Date.valueOf(album.getAlbumDate()));
            statement.setLong(3, album.getSingerId());
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("albumDaoImpl create: error while creating content", e);
        }catch (ClassCastException ex) {
            throw new DaoException("albumDaoImpl update: error while casting entity to Album.class", ex);
        }
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        if (connection == null) {
            throw new DaoException("albumDaoImpl content by id: received null connection");
        }
        if (id == null) {
            return Optional.empty();
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALBUM_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = factory.createSingleContent(resultSet, ContentType.ALBUM);
            }
        } catch (SQLException e) {
            throw new DaoException("albumDaoImpl content by id: error while searching content", e);
        }
        return result;
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

    @Override
    public boolean createContentDescription(AudioContent entity) throws DaoException {
        return false;
    }
}

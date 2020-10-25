package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ext.Genre;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreDaoImpl extends AbstractDao implements ContentDao {

    private static final String SELECT_ALL_GENRE = "SELECT genre_id, genre_name FROM genres ORDER BY genre_name";

    private static final String COLUMN_ID = "genre_id";
    private static final String COLUMN_GENRE_NAME = "genre_name";

    @Override
    public List<AudioContent> findAll() throws DaoException {
        List<AudioContent> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GENRE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Genre genre = createGenreFromResultSet(resultSet);
                result.add(genre);
            }
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
    public boolean update(long id, AudioContent entity) {
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

    private Genre createGenreFromResultSet(ResultSet resultSet) throws SQLException {
        Genre result = new Genre();
        result.setId(resultSet.getInt(COLUMN_ID));
        result.setGenreName(resultSet.getString(COLUMN_GENRE_NAME));
        return result;
    }
}

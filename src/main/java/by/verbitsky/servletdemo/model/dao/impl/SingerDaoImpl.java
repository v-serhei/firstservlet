package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ext.Singer;
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

public class SingerDaoImpl extends AbstractDao implements ContentDao {

    private static final String SELECT_ALL_SINGERS = "Select singer_id, singer_name FROM singers ORDER BY singer_name";

    private static final String COLUMN_ID = "singer_id";
    private static final String COLUMN_SINGER_NAME = "singer_name";


    @Override
    public List<AudioContent> findAll() throws DaoException {
        List<AudioContent> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SINGERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Singer singer = createSingerFromResultSet(resultSet);
                result.add(singer);
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
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException {
        return null;
    }

    @Override
    public long calculateRowCount(ContentFilter filter) throws DaoException {
        return 0;
    }

    private Singer createSingerFromResultSet(ResultSet resultSet) throws SQLException {
        Singer result = new Singer();
        result.setId(resultSet.getInt(COLUMN_ID));
        result.setSingerName(resultSet.getString(COLUMN_SINGER_NAME));
        return result;
    }
}
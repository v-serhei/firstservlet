package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Compilation;
import by.verbitsky.servletdemo.entity.impl.AudioContentFactory;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ext.CompilationFilter;
import by.verbitsky.servletdemo.util.SqlRegexGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompilationDaoImpl extends AbstractDao implements ContentDao {
    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Compilation>();
    private static final String SELECT_COMPILATION_BY_FILTER =
            "Select comp_id, " +
            "       compilation_name, " +
            "       creation_date as compilation_date, " +
            "       cp.compilation_type " +
            "from compilations " +
            "       left join compilations_types cp on cp.type_id = compilations.type_id " +
            "where compilation_name REGEXP ? and cp.compilation_type REGEXP ? " +
            "ORDER BY comp_id " +
            "limit ? offset ?";

    private static final String SELECT_COMPILATION_COUNT =
            "Select count(*) from compilations " +
             "      left join compilations_types as ct on compilations.type_id = ct.type_id " +
             "where compilation_name REGEXP ? and ct.type_id REGEXP ?";

    private static final String SELECT_COMPILATION_TYPES =
            "Select compilation_type from compilations_types;";

    private static final String COLUMN_COMPILATION_TYPE = "compilation_type";


    @Override
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("CompilationDao findFilteredContent: received null filter");
        }
        if (connection == null) {
            throw new DaoException("CompilationDao findFilteredContent: received null connection");
        }
        CompilationFilter compilationFilter = (CompilationFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(compilationFilter.getCompilationTitle());
        String type = SqlRegexGenerator.generateRegexFromParameter(compilationFilter.getCompilationType());
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COMPILATION_BY_FILTER)) {
            statement.setString(1, title);
            statement.setString(2, type);
            statement.setInt(3, limit);
            statement.setLong(4, offset);
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.COMPILATION);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<String> findContentProperties() throws DaoException {
        if (connection == null) {
            throw new DaoException("CompilationDao findContentProperties: received null connection");
        }
        List <String> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COMPILATION_TYPES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(COLUMN_COMPILATION_TYPE));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findContentByUser(User user) throws DaoException {
        return null;
    }

    @Override
    public Optional<AudioContent> findContentByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public long calculateRowCount(ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("SongDao CalculateRowCount: received null filter");
        }
        if (connection == null) {
            throw new DaoException("CompilationDao calculateRowCount: received null connection");
        }
        CompilationFilter compilationFilter = (CompilationFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(compilationFilter.getCompilationTitle());
        String genre = SqlRegexGenerator.generateRegexFromParameter(compilationFilter.getCompilationType());

        long result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COMPILATION_COUNT)) {
            statement.setString(1, title);
            statement.setString(2, genre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<AudioContent> findAll() throws DaoException {
        return null;
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
}

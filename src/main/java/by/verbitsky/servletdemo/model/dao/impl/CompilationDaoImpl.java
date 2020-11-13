package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Compilation;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.ContentFactory;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ext.CompilationFilter;
import by.verbitsky.servletdemo.util.SqlRegexGenerator;

import java.sql.*;
import java.time.LocalDate;
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
                    "where compilation_name regexp ? and cp.compilation_type regexp ? " +
                    "order by comp_id " +
                    "limit ? offset ?";

    private static final String SELECT_COMPILATION_BY_ID =
            "Select comp_id, " +
                    "       compilation_name, " +
                    "       creation_date as compilation_date, " +
                    "       cp.compilation_type " +
                    "from compilations " +
                    "       left join compilations_types cp on cp.type_id = compilations.type_id " +
                    "where comp_id = ?";

    private static final String SELECT_ALL_COMPILATIONS =
            "Select comp_id, " +
                    "       compilation_name, " +
                    "       creation_date as compilation_date, " +
                    "       cp.compilation_type " +
                    "from compilations " +
                    "       left join compilations_types cp on cp.type_id = compilations.type_id " +
                    "order by comp_id ";

    private static final String SELECT_COMPILATION_COUNT =
            "Select count(*) from compilations " +
                    "      left join compilations_types as ct on compilations.type_id = ct.type_id " +
                    "where compilation_name regexp ? and ct.type_id regexp ?";

    private static final String SELECT_COMPILATION_TYPES =
            "Select compilation_type from compilations_types;";

    private static final String INSERT_COMPILATION =
            "Insert Into compilations (compilation_name, creation_date, compilations.type_id) " +
                    "Values (?, ?, " +
                    "        (Select types.type_id from compilations_types as types where compilation_type = ?) );";

    private static final String DELETE_COMPILATION =
                    "Delete from compilations where comp_id=?;";

    private static final String INSERT_DESCRIPTION =
            "Insert Into compilation_description (compilation_id, song_id) values (?, ?);";

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
        List<String> result = new ArrayList<>();
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
    public long calculateRowCount(ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("CompilationDaoImpl CalculateRowCount: received null filter");
        }
        if (connection == null) {
            throw new DaoException("CompilationDaoImpl calculateRowCount: received null connection");
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
        if (connection == null) {
            throw new DaoException("CompilationDaoImpl find all: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COMPILATIONS)) {
            ResultSet resultSet = statement.executeQuery();
            return factory.createContentList(resultSet, ContentType.COMPILATION);
        } catch (SQLException e) {
            throw new DaoException("CompilationDaoImpl find all: error while searching content", e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        if (connection == null) {
            throw new DaoException("CompilationDaoImpl delete: received null connection");
        }
        try (PreparedStatement statement = connection.prepareStatement(DELETE_COMPILATION)) {
            statement.setLong(1, id);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("CompilationDaoImpl delete: SQL error while deleting compilation", e);
        }
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("CompilationDaoImpl create: received null connection");
        }
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_COMPILATION, Statement.RETURN_GENERATED_KEYS)) {
            LocalDate date = ((Compilation) entity).getCompilationCreationDate();
            statement.setString(1, ((Compilation) entity).getCompilationTitle());
            statement.setDate(2, Date.valueOf(date));
            statement.setString(3, ((Compilation) entity).getCompilationType());
            int count = statement.executeUpdate();
            if (count > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("CompilationDaoImpl create: error while creating content", e);
        }
        return result;
    }

    @Override
    public boolean createContentDescription(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("CompilationDaoImpl create description: received null connection");
        }
        if (entity == null) {
            throw new DaoException("CompilationDaoImpl create description: received null parameter");
        }
        Compilation compilation = (Compilation)entity;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DESCRIPTION)) {
            for (Song song : compilation.getSongList()) {
                statement.setLong(1, compilation.getId());
                statement.setLong(2, song.getId());
                int res = statement.executeUpdate();
                if (res == 0) {
                    throw new DaoException("CompilationDaoImpl: error while creating compilation description");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("CompilationDaoImpl create: error while creating content", e);
        }
        return true;
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        if (connection == null) {
            throw new DaoException("CompilationDaoImpl find by id: received null connection");
        }
        if (id == null) {
            throw new DaoException("CompilationDaoImpl find by id: received null connection");
        }
        Optional<AudioContent> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COMPILATION_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                result = factory.createSingleContent(resultSet, ContentType.COMPILATION);
            }
        } catch (SQLException e) {
            throw new DaoException("CompilationDaoImpl find by id: error while creating content", e);
        }
        return result;
    }

    @Override
    public boolean update(AudioContent entity) {
        return false;
    }

    @Override
    public List<AudioContent> findContentByUser(User user) throws DaoException {
        return null;
    }

    @Override
    public Optional<AudioContent> findContentByTitle(String title) {
        return Optional.empty();
    }
}

package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.entity.impl.AudioContentFactory;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ext.ReviewFilter;
import by.verbitsky.servletdemo.util.SqlRegexGenerator;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl extends AbstractDao implements ContentDao {
    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Review>();

    private static final String SELECT_ALL_REVIEWS_BY_SONG_TITLE =
            "SELECT sr.review_id, s.song_id, s.song_title, si.singer_name, u.username, sr.review, sr.user_id " +
                    "from song_review as sr " +
                    "left join songs s on s.song_id = sr.song_id " +
                    "left join users u on sr.user_id = u.user_id " +
                    "left join singers si on s.singer_id = si.singer_id " +
                    "where s.song_id IN (SELECT song_id FROM songs where song_title REGEXP ?)";

    private static final String SELECT_SONG_COUNT =
            "SELECT COUNT(*) " +
                    "from (Select COUNT(DISTINCT song_id) " +
                    "      from song_review as sr " +
                    "      where song_id IN (SELECT song_id FROM songs where song_title REGEXP ?) " +
                    "      group by song_id) as rcount";

    private static final String SELECT_USER_REVIEWS =
            "SELECT sr.review_id, s.song_id, s.song_title, si.singer_name, users.username, sr.review, sr.user_id " +
                    "from song_review as sr" +
                    "         left join songs s on s.song_id = sr.song_id" +
                    "         left join users on sr.user_id = users.user_id" +
                    "         left join singers si on s.singer_id = si.singer_id " +
                    "where sr.user_id = ?;";

    private static final String SELECT_ALL_REVIEWS =
            "SELECT sr.review_id, s.song_id, s.song_title, si.singer_name, users.username, sr.review, sr.user_id " +
                    "from song_review as sr" +
                    "         left join songs s on s.song_id = sr.song_id" +
                    "         left join users on sr.user_id = users.user_id" +
                    "         left join singers si on s.singer_id = si.singer_id order by song_id";

    private static final String SELECT_UNIQ_SONG_TITLES =
            "SELECT sr.review_id, s.song_id, s.song_title, si.singer_name, users.username, sr.review, sr.user_id " +
                    "from song_review as sr " +
                    "         left join songs s on s.song_id = sr.song_id " +
                    "         left join users on sr.user_id = users.user_id " +
                    "         left join singers si on s.singer_id = si.singer_id " +
                    "where s.song_id IN (SELECT song_id  FROM songs where song_title REGEXP ?) " +
                    "group by song_title " +
                    "order by song_title " +
                    "limit ? offset ?";

    private static final String INSERT_REVIEW =
            "INSERT INTO song_review (song_id, user_id, review) VALUES (?, ?, ?);";

    private static final String DELETE_REVIEW_BY_ID =
            "DELETE FROM song_review WHERE song_review.review_id = ?";

    private static final String SELECT_REVIEW_BY_ID =
            "SELECT sr.review_id, s.song_id, s.song_title, si.singer_name, users.username, sr.review, sr.user_id " +
                    "from song_review as sr" +
                    "         left join songs s on s.song_id = sr.song_id" +
                    "         left join users on sr.user_id = users.user_id" +
                    "         left join singers si on s.singer_id = si.singer_id " +
                    "where sr.review_id = ?;";


    @Override
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("ReviewDaoImpl findFilteredContent: received null filter");
        }
        if (connection == null) {
            throw new DaoException("ReviewDaoImpl findFilteredContent: received null connection");
        }
        ReviewFilter reviewFilter = (ReviewFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(reviewFilter.getSongTitle());
        List<AudioContent> result;
        boolean unique = ((ReviewFilter) filter).isFindUnique();
        String query;
        if (unique) {
            query = SELECT_UNIQ_SONG_TITLES;
        } else {
            query = SELECT_ALL_REVIEWS_BY_SONG_TITLE;
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            if (unique) {
                statement.setInt(2, limit);
                statement.setLong(3, offset);
            }
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.REVIEW);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<String> findContentProperties() {
        return null;
    }

    @Override
    public long calculateRowCount(ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("ReviewDaoImpl CalculateRowCount: received null filter");
        }
        if (connection == null) {
            throw new DaoException("ReviewDaoImpl CalculateRowCount: received null connection");
        }
        ReviewFilter reviewFilter = (ReviewFilter) filter;
        String title = SqlRegexGenerator.generateRegexFromParameter(reviewFilter.getSongTitle());
        long result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SONG_COUNT)) {
            statement.setString(1, title);
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
            throw new DaoException("ReviewDaoImpl create: connection is null");
        }
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REVIEWS)) {
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.REVIEW);
        } catch (SQLException e) {
            throw new DaoException("ReviewDaoImpl findContentByUser: SQL error while searching user reviews", e);
        }
        return result;
    }

    @Override
    public boolean update(AudioContent entity) {
        return false;
    }

    @Override
    public Optional<AudioContent> findEntityById(Long id) throws DaoException {
        if (id == 0) {
            throw new DaoException("ReviewDaoImpl findEntityById: received id = 0");
        }
        if (connection == null) {
            throw new DaoException("ReviewDaoImpl findEntityById: received null connection");
        }
        Optional<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_REVIEW_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = factory.createSingleContent(set, ContentType.REVIEW);
            } else {
                result = Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("ReviewDaoImpl findEntityById: SQL error while searching review by id", e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        if (id == 0) {
            throw new DaoException("ReviewDaoImpl findEntityById: received id = 0");
        }
        if (connection == null) {
            throw new DaoException("ReviewDaoImpl findEntityById: received null connection");
        }
        boolean result;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW_BY_ID)) {
            statement.setLong(1, id);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("ReviewDaoImpl findEntityById: SQL error while searching review by id", e);
        }
    }

    @Override
    public boolean create(AudioContent entity) throws DaoException {
        if (connection == null) {
            throw new DaoException("ReviewDaoImpl create: connection is null");
        }
        if (entity == null) {
            throw new DaoException("ReviewDaoImpl create: received null review");
        }
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_REVIEW)) {
            statement.setLong(1, ((Review) entity).getSongId());
            statement.setLong(2, ((Review) entity).getUserId());
            statement.setString(3, ((Review) entity).getReviewText());
            int res = statement.executeUpdate();
            if (res > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException("ReviewDaoImpl create review: error while creating create in db");
        }
        return result;
    }

    @Override
    public List<AudioContent> findContentByUser(User user) throws DaoException {
        if (connection == null) {
            throw new DaoException("ReviewDaoImpl create: connection is null");
        }
        List<AudioContent> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_REVIEWS)) {
            statement.setLong(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            result = factory.createContentList(resultSet, ContentType.REVIEW);
        } catch (SQLException e) {
            throw new DaoException("ReviewDaoImpl findContentByUser: SQL error while searching user reviews", e);
        }
        return result;
    }
}

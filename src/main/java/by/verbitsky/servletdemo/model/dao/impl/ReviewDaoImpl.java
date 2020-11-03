package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentFactory;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.entity.impl.AudioContentFactory;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.service.ContentFilter;
import by.verbitsky.servletdemo.model.service.ext.ReviewFilter;
import by.verbitsky.servletdemo.util.SqlRegexGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl extends AbstractDao implements ContentDao {
    private static final ContentFactory<AudioContent> factory = new AudioContentFactory<Review>();
    private static final String SELECT_ALL_REVIEWS_BY_SONG_TITLE =
            "SELECT sr.review_id, s.song_id, s.song_title, si.singer_name, u.username, sr.review " +
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

    private static final String SELECT_UNIQ_SONG_TITLES =
            "SELECT sr.review_id, s.song_id, s.song_title, si.singer_name, users.username, sr.review " +
                    "from song_review as sr " +
                    "         left join songs s on s.song_id = sr.song_id " +
                    "         left join users on sr.user_id = users.user_id " +
                    "         left join singers si on s.singer_id = si.singer_id " +
                    "where s.song_id IN (SELECT song_id  FROM songs where song_title REGEXP ?) " +
                    "group by song_title " +
                    "order by song_title " +
                    "limit ? offset ?";

    @Override
    public List<AudioContent> findFilteredContent(long offset, int limit, ContentFilter filter) throws DaoException {
        if (filter == null) {
            throw new DaoException("ReviewDao findFilteredContent: received null filter");
        }
        if (connection == null) {
            throw new DaoException("ReviewDao findFilteredContent: received null connection");
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
            throw new DaoException("ReviewDao CalculateRowCount: received null filter");
        }
        if (connection == null) {
            throw new DaoException("ReviewDao CalculateRowCount: received null connection");
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
        return null;
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
}
